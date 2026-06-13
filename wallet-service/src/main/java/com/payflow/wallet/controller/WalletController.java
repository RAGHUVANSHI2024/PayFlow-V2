package com.payflow.wallet.controller;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping()
    public void createWallet(@RequestBody CreateWalletRequest request){
        walletService.createWallet(request);
    }
}

