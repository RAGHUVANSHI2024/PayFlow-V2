package com.payflow.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {

    @NotNull
    @Schema(
            description = "Sender Wallet Id",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long senderWalletId;

    @Schema(
            description = "Receiver Wallet ID",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Long receiverWalletId;

    @Schema(
            description = "Transfer Amount",
            example = "500.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @DecimalMin("1.00")
    private BigDecimal amount;

}