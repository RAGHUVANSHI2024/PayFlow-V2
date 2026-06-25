package com.payflow.wallet.controller;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping()
    public void createWallet(@RequestBody CreateWalletRequest request){
        walletService.createWallet(request);
    }
    @GetMapping("/balance/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable() Long walletId){

        return ResponseEntity.ok(walletService.getWalletBalance(walletId));
    }
}

