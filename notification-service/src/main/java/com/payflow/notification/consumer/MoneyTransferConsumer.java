package com.payflow.notification.consumer;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoneyTransferConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "money-transfer-topic",
            groupId = "notification-group"
    )
    public void consume(MoneyTransferredEvent event) {

        notificationService.sendNotification(event);

        log.info("Transfer notification saved successfully.");
    }

}