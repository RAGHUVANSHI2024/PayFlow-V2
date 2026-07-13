package com.payflow.audit.consumer;

import com.payflow.audit.dto.NotificationFailedEvent;
import com.payflow.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationFailedConsumer {

    private final AuditService auditService;

    @KafkaListener(
            topics = "notification-failed-topic",
            groupId = "audit-group"
    )
    public void notificationFailed(NotificationFailedEvent event){

        auditService.createNotificationFailed(event);

        log.info(
                "NotificationFailedEvent received : {}",
                event.getEventId()
        );    }
}
