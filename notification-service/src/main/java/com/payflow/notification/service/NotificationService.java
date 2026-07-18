package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.dto.SendNotificationCommand;

import java.util.concurrent.ExecutionException;

public interface NotificationService {

    void sendNotification(MoneyTransferredEvent command);

}