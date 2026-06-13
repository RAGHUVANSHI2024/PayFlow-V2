package com.payflow.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;

}