package com.payflow.wallet.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundMoneyCommand {

    private String eventId;

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;
}