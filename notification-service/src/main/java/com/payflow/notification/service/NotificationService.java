package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;

import java.util.concurrent.ExecutionException;

public interface NotificationService {

    void createTransferNotification(MoneyTransferredEvent event) throws ExecutionException, InterruptedException;

}