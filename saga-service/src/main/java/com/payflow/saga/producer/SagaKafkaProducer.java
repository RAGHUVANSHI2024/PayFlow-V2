package com.payflow.saga.producer;

import com.payflow.saga.dto.CreditMoneyCommand;
import com.payflow.saga.dto.DebitMoneyCommand;
import com.payflow.saga.dto.RefundMoneyCommand;
import com.payflow.saga.dto.SendNotificationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class SagaKafkaProducer {
    private static final String DEBIT_TOPIC = "debit-money-topic";

    private static final String CREDIT_TOPIC = "credit-money-topic";

    private static final String SEND_NOTIFICATION_TOPIC = "send-notification-topic";

    private static final String REFUND_TOPIC = "refund-money-topic";

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public CompletableFuture<SendResult<String, Object>>
    publishDebitCommand(DebitMoneyCommand command){

        return kafkaTemplate.send(
                DEBIT_TOPIC,
                command.getSenderWalletId().toString(),
                command
        );
    }

    public CompletableFuture<SendResult<String, Object>>
    publishCreditCommand(CreditMoneyCommand command){
        return kafkaTemplate.send(
               CREDIT_TOPIC,
               command.getEventId(),
                command
        );
    }

    public CompletableFuture<SendResult<String, Object>>
    publishSendNotification(SendNotificationCommand command){
        return kafkaTemplate.send(
                SEND_NOTIFICATION_TOPIC,
                command.getEventId(),
                command
        );

    }

    public CompletableFuture<SendResult<String, Object>>
    publishRefundCommand(RefundMoneyCommand command){
        return kafkaTemplate.send(
                REFUND_TOPIC,
                command.getEventId(),
                command
        );

    }


}
