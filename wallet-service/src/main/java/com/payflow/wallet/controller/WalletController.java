package com.payflow.wallet.controller;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
@Tag(
        name = "Wallet Management",
        description = "Operations related to wallet creation and balance management."
)
public class WalletController {

    private final WalletService walletService;

    @PostMapping()
    @Operation(
            summary = "Create Wallet",
            description = "Create a wallet for a registered user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Wallet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Wallet already exists")
    })
    public ResponseEntity<com.payflow.wallet.response.ApiResponse<Void>> createWallet(@RequestBody CreateWalletRequest request){
        walletService.createWallet(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.payflow.wallet.response.ApiResponse.<Void>builder()
                        .status(201)
                        .message("Wallet created successfully")
                        .build());
    }


    @GetMapping("/balance/{walletId}")
    @Operation(
            summary = "Get Wallet Balance",
            description = "Return current wallet balance"
    )
    @Parameter(
            description = "Wallet Id",
            example = "1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<com.payflow.wallet.response.ApiResponse<BigDecimal>> getBalance(
            @Parameter(description = "Wallet ID", example = "1", required = true)
            @PathVariable Long walletId) {

        BigDecimal balance = walletService.getWalletBalance(walletId);

        return ResponseEntity.ok(
                com.payflow.wallet.response.ApiResponse.<BigDecimal>builder()
                        .status(HttpStatus.OK.value())
                        .message("Balance fetched successfully")
                        .data(balance)
                        .build()
        );
    }
}

