package com.payflow.notification.consumer;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoneyTransferConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "money-transfer-topic",
            groupId = "notification-group"
    )
    public void consume(MoneyTransferredEvent event){

        notificationService.createTransferNotification(event);

        log.info("Transfer notification saved successfully.");
    }

}