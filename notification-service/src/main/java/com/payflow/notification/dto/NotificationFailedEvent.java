package com.payflow.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationFailedEvent {

    private String eventId;

    private String originalEventId;

    private Long senderUserId;

    private Long receiverUserId;

    private String reason;

    private LocalDateTime failedAt;

}
