package com.payflow.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferredEvent {

    private Long senderUserId;
    private Long receiverUserId;
    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
}
