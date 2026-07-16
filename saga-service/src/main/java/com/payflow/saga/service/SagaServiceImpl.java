package com.payflow.saga.service;

import com.payflow.saga.dto.*;
import com.payflow.saga.producer.SagaKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SagaServiceImpl implements SagaService{

    private final SagaKafkaProducer sagaKafkaProducer;

    @Override
    public void moneyCredited(MoneyCreditedEvent event) {
        log.info("Money credited successfully : {}" , event.getEventId());

        SendNotificationCommand command = SendNotificationCommand.builder()
                .eventId(event.getEventId())
                .senderUserId(event.getSenderWalletId())
                .receiverUserId(event.getReceiverWalletId())
                .amount(event.getAmount())
                .build();

        sagaKafkaProducer.publishSendNotification(command)
                .thenAccept(result ->
                        log.info("Notification command published : {}",
                                command.getEventId()
                        ))
                .exceptionally(ex -> {

            log.error(
                    "Notification command failed",
                    ex);

            return null;
        });
    }
    @Override
    public void moneyCreditFailed(MoneyCreditFailedEvent event) {

        log.info("Money credit failed : {}",event.getEventId());

        RefundMoneyCommand command = RefundMoneyCommand.builder()
                .eventId(event.getEventId())
                .senderWalletId(event.getSenderWalletId())
                .receiverWalletId(event.getReceiverWalletId())
                .build();

        sagaKafkaProducer.publishRefundCommand(command);
    }

    @Override
    public void moneyDebited(MoneyDebitedEvent event) {

        log.info("Money debited successfully : {}",event.getEventId());

        CreditMoneyCommand creditMoneyCommand = CreditMoneyCommand.builder()
                .eventId(event.getEventId())
                .senderWalletId(event.getSenderWalletId())
                .receiverWalletId(event.getReceiverWalletId())
                .amount(event.getAmount())
                .build();

        sagaKafkaProducer.publishCreditCommand(creditMoneyCommand)
                .thenAccept(command ->
                        log.info(
                                "Money credit command published : {}",
                                  event.getEventId()
                        ))
                .exceptionally(ex -> {
                    log.error("Money credit command failed : {}",ex);

                    return null;

                });
    }

    @Override
    public void processTransfer(TransferRequestedEvent event) {

        DebitMoneyCommand command = DebitMoneyCommand.builder()
                .eventId(event.getEventId())
                .senderWalletId(event.getSenderWalletId())
                .receiverWalletId(event.getReceiverWalletId())
                .amount(event.getAmount())
                .build();

        sagaKafkaProducer.publishDebitCommand(command)
                .thenAccept(result ->
                        log.info("Debit Command Published : {}",
                                command.getEventId())
                        )
                .exceptionally(ex -> {
                    log.error("Failed to publish debit command ",ex);

                    return null;
                });

    }
    @Override
    public void moneyRefunded(MoneyRefundedEvent event) {

        log.info(
                "Saga completed with compensation : {}",
                event.getEventId()
        );

    }
}
