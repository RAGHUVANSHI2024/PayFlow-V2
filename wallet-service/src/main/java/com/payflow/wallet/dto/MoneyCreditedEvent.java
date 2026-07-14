package com.payflow.wallet.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyCreditedEvent {

    private String eventId;

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;

    private LocalDateTime creditedAt;
}