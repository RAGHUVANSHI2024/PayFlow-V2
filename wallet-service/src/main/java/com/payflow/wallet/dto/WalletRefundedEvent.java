package com.payflow.wallet.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRefundedEvent {

    private String eventId;

    private String originalEventId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal refundedAmount;

    private LocalDateTime refundedAt;
}