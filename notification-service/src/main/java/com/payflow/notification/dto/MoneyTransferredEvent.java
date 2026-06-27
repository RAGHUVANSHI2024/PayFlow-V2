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

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;

    private LocalDateTime transactionTime;

}