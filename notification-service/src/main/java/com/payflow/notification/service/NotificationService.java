package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;

public interface NotificationService {

    void createTransferNotification(MoneyTransferredEvent event);

}