package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.entity.Notification;
import com.payflow.notification.entity.ProcessedEvent;
import com.payflow.notification.enums.NotificationType;
import com.payflow.notification.repository.NotificationRepository;
import com.payflow.notification.repository.ProcessedEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final ProcessedEventRepository processedEventRepository;
    @Override
    public void createTransferNotification(MoneyTransferredEvent event) {

        if (processedEventRepository.existsByEventId(event.getEventId())){
            log.info("Duplicate Event Ignored : {}",event.getEventId());
            return;
        }
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

        ProcessedEvent processedEvent = ProcessedEvent.builder()
                .eventId(event.getEventId())
                .processedAt(LocalDateTime.now())
                .build();

        processedEventRepository.save(processedEvent);
    }
}