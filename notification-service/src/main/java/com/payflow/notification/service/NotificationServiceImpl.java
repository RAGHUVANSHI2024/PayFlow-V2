package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.dto.NotificationCreatedEvent;
import com.payflow.notification.dto.NotificationFailedEvent;
import com.payflow.notification.dto.SendNotificationCommand;
import com.payflow.notification.entity.Notification;
import com.payflow.notification.entity.ProcessedEvent;
import com.payflow.notification.enums.NotificationType;
import com.payflow.notification.kafka.NotificationKafkaProducer;
import com.payflow.notification.repository.NotificationRepository;
import com.payflow.notification.repository.ProcessedEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final ProcessedEventRepository processedEventRepository;

    private final NotificationKafkaProducer notificationKafkaProducer;

    @Override
    public void sendNotification(MoneyTransferredEvent command) {

        try {

            if (processedEventRepository.existsByEventId(command.getEventId())) {
                log.info("Duplicate Event Ignored : {}", command.getEventId());
                return;
            }
            Notification senderNotification = Notification.builder()
                    .userId(command.getSenderUserId())
                    .message("You sent ₹" + command.getAmount() + " successfully.")
                    .type(NotificationType.TRANSFER)
                    .isRead(false)
                    .build();

            Notification receiverNotification = Notification.builder()
                    .userId(command.getReceiverUserId())
                    .message("You received ₹" + command.getAmount() + " successfully.")
                    .type(NotificationType.TRANSFER)
                    .isRead(false)
                    .build();

            notificationRepository.save(senderNotification);
            notificationRepository.save(receiverNotification);

            ProcessedEvent processedEvent = ProcessedEvent.builder()
                    .eventId(command.getEventId())
                    .processedAt(LocalDateTime.now())
                    .build();

            processedEventRepository.save(processedEvent);

            NotificationCreatedEvent createdEvent = NotificationCreatedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .originalEventId(command.getEventId())
                    .senderUserId(command.getReceiverUserId())
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationKafkaProducer.publishNotificationCreated(createdEvent)
                    .thenAccept(result ->

                            log.info("NotificationCreatedEvent published : {}",
                                    createdEvent.getOriginalEventId()))

                    .exceptionally(ex -> {
                        log.error("Failed to publish Notification ", ex
                        );

                        return null;
                    });
        }catch (Exception ex){

            NotificationFailedEvent failedEvent = NotificationFailedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .originalEventId(command.getEventId())
                    .senderUserId(command.getSenderUserId())
                    .receiverUserId(command.getReceiverUserId())
                    .reason(ex.getMessage())
                    .failedAt(LocalDateTime.now())
                    .build();

            notificationKafkaProducer.publishNotificationFailed(failedEvent)
                    .thenAccept(result ->
                            log.info("NotificationFailedEvent published : {}",
                                    failedEvent.getOriginalEventId()))
                    .exceptionally(e -> {
                        log.error("Unable to publish NotificationFailedEvent", e);
                        return null;
                    });
        }
    }
}