package com.payflow.notification.kafka;

import com.payflow.notification.dto.NotificationCreatedEvent;
import com.payflow.notification.dto.NotificationFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationKafkaProducer {

    private final KafkaTemplate<String , Object> kafkaTemplate;

    private static final String NOTIFICATION_CREATED_TOPIC =
            "notification-created-topic";

    private static final String NOTIFICATION_FAILED_TOPIC =
            "notification-failed-topic";

    public CompletableFuture<SendResult<String, Object>>
    publishNotificationCreated(NotificationCreatedEvent event){

        return kafkaTemplate.send(
                NOTIFICATION_CREATED_TOPIC,
                event.getOriginalEventID(),
                event
        );
    }

    public CompletableFuture<org.springframework.kafka.support.SendResult<String, Object>>
    publishNotificationFailed(NotificationFailedEvent event){

        return kafkaTemplate.send(
                NOTIFICATION_FAILED_TOPIC,
                event.getOriginalEventId(),
                event
        );
    }
}
