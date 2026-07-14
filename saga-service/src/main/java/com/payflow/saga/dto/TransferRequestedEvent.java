package com.payflow.saga.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestedEvent {

    private String eventId;

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;

    private LocalDateTime createdAt;
}