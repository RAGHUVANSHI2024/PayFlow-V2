package com.payflow.notification.consumer;

import com.payflow.notification.dto.SendNotificationCommand;
import com.payflow.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendNotificationConsumer {

    public final NotificationService notificationService;

    @KafkaListener(
            topics = "send-notification-topic",
            groupId = "notification-group"
    )
    public void consume(SendNotificationCommand command) {
        notificationService.sendNotification(command);
    }
}
