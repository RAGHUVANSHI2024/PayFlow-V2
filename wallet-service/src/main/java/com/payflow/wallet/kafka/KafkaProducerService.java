package com.payflow.wallet.kafka;

import com.payflow.wallet.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String MONEY_TRANSFER = "money-transfer-topic";

    private static final String MONEY_DEBIT_FAILED = "money-debit-failed-topic";

    private static final String MONEY_DEBITED = "money-debited-topic";

    private static final String MONEY_CREDITED = "money-credited-topic";

    private static final String MONEY_CREDITED_FAILED = "money-credit-failed-topic";

    private static final String MONEY_REFUNDED_EVENT = "money-refunded-topic";

    private static final String TRANSFER_REQUESTED =
            "transfer-requested-topic";
    public CompletableFuture<SendResult<String, Object>>
    sendMoneyTransferredEvent(MoneyTransferredEvent event) {

        return kafkaTemplate.send(
                MONEY_TRANSFER,
                event.getSenderUserId().toString(),
                event
        );
    }
    public CompletableFuture<SendResult<String, Object>>
    sendMoneyTransferRequestEvent(
            TransferRequestedEvent event) {

        return kafkaTemplate.send(
                TRANSFER_REQUESTED,
                event.getSenderWalletId().toString(),
                event
        );
    }

    public CompletableFuture<SendResult<String,Object>>
    publishMoneyDebitFailed(MoneyDebitFailedEvent event){

        return kafkaTemplate.send(
                MONEY_DEBIT_FAILED,
                event.getEventId(),
                event
        );
    }

    public CompletableFuture<SendResult<String , Object>>
    publishMoneyDebited(MoneyDebitedEvent event){

        return kafkaTemplate.send(
                MONEY_DEBITED,
                event.getEventId(),
                event
        );
    }

    public CompletableFuture<SendResult<String,Object>>
    publishMoneyCredited(MoneyCreditedEvent event){

        return kafkaTemplate.send(
                MONEY_CREDITED,
                event.getEventId(),
                event
        );
    }
    public CompletableFuture<SendResult<String,Object>>
    publishMoneyCreditFailed(MoneyCreditFailedEvent event){

        return kafkaTemplate.send(
                MONEY_CREDITED_FAILED,
                event.getEventId(),
                event
        );
    }

    public CompletableFuture<SendResult<String,Object>>
    publishMoneyRefunded(MoneyRefundedEvent event) {

         return kafkaTemplate.send(
                MONEY_REFUNDED_EVENT,
                event.getEventId(),
                event
        );
    }
}