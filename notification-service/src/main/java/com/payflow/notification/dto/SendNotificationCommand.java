package com.payflow.notification.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendNotificationCommand {

    private String eventId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal amount;

}