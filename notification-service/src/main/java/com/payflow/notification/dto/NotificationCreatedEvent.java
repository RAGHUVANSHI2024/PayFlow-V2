package com.payflow.notification.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationCreatedEvent {

    private String eventId;

    private String originalEventID;

    private Long senderUserId;

    private Long receiverUserId;

    private LocalDateTime createdAt;
}
