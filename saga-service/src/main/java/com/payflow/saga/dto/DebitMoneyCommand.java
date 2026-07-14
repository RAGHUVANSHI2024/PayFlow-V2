package com.payflow.saga.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebitMoneyCommand {

    private String eventId;

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;
}