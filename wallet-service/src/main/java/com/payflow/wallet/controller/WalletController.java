package com.payflow.wallet.controller;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
@Tag(
        name = "Wallet Management",
        description = "Operation related to wallet creation and balance management"
)
public class WalletController {

    private final WalletService walletService;

    @PostMapping()
    @Operation(
            summary = "Create Wallet",
            description = "Create a wallet for a registered user"
    )
    public void createWallet(@RequestBody CreateWalletRequest request){
        walletService.createWallet(request);
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
    public ResponseEntity<BigDecimal> getBalance(@PathVariable() Long walletId){

        return ResponseEntity.ok(walletService.getWalletBalance(walletId));
    }
}

