package com.payflow.notification.consumer;

import com.payflow.notification.dto.MoneyTransferredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MoneyTransferConsumer {

    @KafkaListener(
            topics = "money-transfer-topic",
            groupId = "notification-group"
    )
    public void consume(MoneyTransferredEvent event){

        log.info("========================================");
        log.info("Money Transfer Notification Received");
        log.info("Sender Wallet : {}", event.getSenderWalletId());
        log.info("Receiver Wallet : {}", event.getReceiverWalletId());
        log.info("Amount : {}", event.getAmount());
        log.info("Transaction Time : {}", event.getTransactionTime());
        log.info("========================================");

    }

}