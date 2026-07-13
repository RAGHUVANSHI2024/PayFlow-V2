package com.payflow.audit.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationCreatedEvent {

    private String eventId;

    private String originalEventId;

    private Long senderUserId;

    private Long receiverUserId;

    private LocalDateTime createdAt;
}
