package com.payflow.audit.consumer;

import com.payflow.audit.dto.NotificationCreatedEvent;
import com.payflow.audit.service.AuditService;
import jakarta.websocket.SendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationCreatedConsumer {

    private final AuditService auditService;

    @KafkaListener(
            topics = "notification-created-topic",
            groupId = "audit-group"
    )
    public void notificationCreated(NotificationCreatedEvent event){

        auditService.createNotificationCreated(event);

        log.info(
                "NotificationCreatedEvent received : {}",
                event.getEventId()
        );
    }
}
