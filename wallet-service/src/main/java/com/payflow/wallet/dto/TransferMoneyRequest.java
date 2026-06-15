package com.payflow.wallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {

    @NotNull
    private Long senderWalletId;

    @NotNull
    private Long receiverWalletId;

    @NotNull
    @DecimalMin("1.00")
    private BigDecimal amount;

}