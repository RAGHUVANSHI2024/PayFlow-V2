package com.payflow.audit.service;

import com.payflow.audit.dto.NotificationCreatedEvent;
import com.payflow.audit.dto.NotificationFailedEvent;

public interface AuditService {

    void createNotificationCreated(NotificationCreatedEvent event);

    void createNotificationFailed(NotificationFailedEvent event);
}
