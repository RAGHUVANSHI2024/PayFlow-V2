package com.payflow.notification.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyTransferredEvent {

    private String eventId;

    private Long senderUserId;

    private Long receiverUserId;

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;

    private String status;

    private LocalDateTime transactionTime;

}