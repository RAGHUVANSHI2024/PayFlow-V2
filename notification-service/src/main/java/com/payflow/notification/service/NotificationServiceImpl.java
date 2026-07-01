package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.entity.Notification;
import com.payflow.notification.enums.NotificationType;
import com.payflow.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void createTransferNotification(MoneyTransferredEvent event) {

        Notification senderNotification = Notification.builder()
                .userId(event.getSenderUserId())
                .message("You sent ₹" + event.getAmount() + " successfully.")
                .type(NotificationType.TRANSFER)
                .isRead(false)
                .build();

        Notification receiverNotification = Notification.builder()
                .userId(event.getReceiverUserId())
                .message("You received ₹" + event.getAmount() + " successfully.")
                .type(NotificationType.TRANSFER)
                .isRead(false)
                .build();

        notificationRepository.save(senderNotification);
        notificationRepository.save(receiverNotification);
    }
}