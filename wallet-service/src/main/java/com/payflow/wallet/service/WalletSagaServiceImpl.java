package com.payflow.wallet.service;

import com.payflow.wallet.Exception.WalletIdNotFoundException;
import com.payflow.wallet.dto.*;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.kafka.KafkaProducerService;
import com.payflow.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletSagaServiceImpl implements WalletSagaService{

    private final WalletRepository walletRepository;

    private final WalletCacheService walletCacheService;

    private final KafkaProducerService kafkaProducerService;

    @Override
    public void debit(DebitMoneyCommand command) {

        Wallet sender = walletRepository.findById(command.getSenderWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (sender.getBalance().compareTo(command.getAmount()) < 0){

            MoneyDebitFailedEvent debitFailedEvent = MoneyDebitFailedEvent.builder()
                    .eventId(command.getEventId())
                    .senderWalletId(command.getSenderWalletId())
                    .receiverWalletId(command.getReceiverWalletId())
                    .reason("Insufficient Money !")
                    .build();

            kafkaProducerService.publishMoneyDebitFailed(debitFailedEvent);

            return;
        }

        sender.setBalance(sender.getBalance().subtract(command.getAmount()));

        walletCacheService.evictBalance(sender.getId());

        walletRepository.save(sender);

        MoneyDebitedEvent debitedEvent =
                MoneyDebitedEvent.builder()
                        .eventId(command.getEventId())
                        .senderWalletId(command.getSenderWalletId())
                        .receiverWalletId(command.getReceiverWalletId())
                        .amount(command.getAmount())
                        .debitedAt(LocalDateTime.now())
                        .build();

        kafkaProducerService.publishMoneyDebited(debitedEvent)
                .thenAccept(result ->
                        log.info(
                                "Money Debited Success : {} ",
                                debitedEvent.getEventId()
                        )
                        )
                .exceptionally(ex -> {
                    log.error("Money Debit Failed : {}",ex);

                    return null;
                });

    }

    @Override
    public void credit(CreditMoneyCommand command) {

        try {

            Wallet receiver = walletRepository.findById(command.getReceiverWalletId())
                    .orElseThrow(() -> new RuntimeException("Receiver Wallet does not exist"));

            receiver.setBalance(receiver.getBalance().add(command.getAmount()));

            walletRepository.save(receiver);

            walletCacheService.evictBalance(receiver.getId());

            MoneyCreditedEvent creditedEvent = MoneyCreditedEvent.builder()
                    .eventId(command.getEventId())
                    .senderWalletId(command.getSenderWalletId())
                    .receiverWalletId(command.getReceiverWalletId())
                    .amount(command.getAmount())
                    .creditedAt(LocalDateTime.now())
                    .build();

            kafkaProducerService.publishMoneyCredited(creditedEvent)
                    .thenAccept(result ->
                            log.info("Money credited : {}", creditedEvent.getEventId()))
                    .exceptionally(ex -> {

                        log.error("Failed to publish MoneyCreditedEvent", ex);

                        return null;
                    });

        }catch (Exception ex){

            MoneyCreditFailedEvent failed =
                    MoneyCreditFailedEvent.builder()
                            .eventId(command.getEventId())
                            .senderWalletId(command.getSenderWalletId())
                            .receiverWalletId(command.getReceiverWalletId())
                            .reason(ex.getMessage())
                            .build();

            kafkaProducerService.publishMoneyCreditFailed(failed);
        }
    }

    @Override
    @Transactional
    public void refund(RefundMoneyCommand command) {

        Wallet sender = walletRepository.findById(command.getSenderWalletId())
                .orElseThrow(() ->
                        new WalletIdNotFoundException("Sender wallet not found"));

        sender.setBalance(
                sender.getBalance().add(command.getAmount()));

        walletRepository.save(sender);

        walletCacheService.evictBalance(sender.getId());

        MoneyRefundedEvent event =
                MoneyRefundedEvent.builder()
                        .eventId(command.getEventId())
                        .senderWalletId(command.getSenderWalletId())
                        .receiverWalletId(command.getReceiverWalletId())
                        .amount(command.getAmount())
                        .refundedAt(LocalDateTime.now())
                        .build();

        kafkaProducerService.publishMoneyRefunded(event)
                .thenAccept(result ->
                        log.info(
                                "Money refunded successfully : {}",
                                event.getEventId()))
                .exceptionally(ex -> {

                    log.error(
                            "Failed to publish MoneyRefundedEvent",
                            ex);

                    return null;
                });
    }
}
