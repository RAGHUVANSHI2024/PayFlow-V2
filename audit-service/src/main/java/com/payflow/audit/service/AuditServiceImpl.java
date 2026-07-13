package com.payflow.audit.service;

import com.payflow.audit.dto.NotificationCreatedEvent;
import com.payflow.audit.dto.NotificationFailedEvent;
import com.payflow.audit.entity.AuditLogs;
import com.payflow.audit.enums.AuditEventType;
import com.payflow.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void createNotificationCreated(NotificationCreatedEvent event) {

        AuditLogs auditLogs = AuditLogs.builder()
                .eventId(event.getEventId())
                .eventType(AuditEventType.NOTIFICATION_CREATED)
                .serviceName("notification-service")
                .description(
                        "Notification created for event :" +
                        event.getOriginalEventId()
                )
                .createdAt(LocalDateTime.now())
                .build();

        auditRepository.save(auditLogs);


    }
    @Override
    public void createNotificationFailed(NotificationFailedEvent event) {

        AuditLogs auditLogs = AuditLogs.builder()
                .eventId(event.getEventId())
                .eventType(AuditEventType.NOTIFICATION_FAILED)
                .serviceName("notification-service")
                .description(
                        "Notification failed :" +
                        event.getReason()
                )
                .createdAt(LocalDateTime.now())
                .build();

        auditRepository.save(auditLogs);
    }
}
