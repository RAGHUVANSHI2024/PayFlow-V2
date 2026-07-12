package com.payflow.notification.service;

import com.payflow.notification.dto.MoneyTransferredEvent;
import com.payflow.notification.dto.NotificationCreatedEvent;
import com.payflow.notification.dto.NotificationFailedEvent;
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
    public void createTransferNotification(MoneyTransferredEvent event) throws ExecutionException, InterruptedException {

        try {

            if (processedEventRepository.existsByEventId(event.getEventId())) {
                log.info("Duplicate Event Ignored : {}", event.getEventId());
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

            NotificationCreatedEvent createdEvent = NotificationCreatedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .originalEventID(event.getEventId())
                    .senderUserId(event.getSenderUserId())
                    .receiverUserId(event.getReceiverUserId())
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationKafkaProducer.publishNotificationCreated(createdEvent)
                    .thenAccept(result ->

                            log.info("NotificationCreatedEvent published : {}",
                                    createdEvent.getOriginalEventID()))

                    .exceptionally(ex -> {
                        log.error("Failed to publish Notification ", ex
                        );

                        return null;
                    });
        }catch (Exception ex){

            NotificationFailedEvent failedEvent = NotificationFailedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .originalEventId(event.getEventId())
                    .senderUserId(event.getSenderUserId())
                    .receiverUserId(event.getReceiverUserId())
                    .reason(ex.getMessage())
                    .failedAt(LocalDateTime.now())
                    .build();

            notificationKafkaProducer.publishNotificationFailed(failedEvent).get();

            throw ex;
        }
    }
}