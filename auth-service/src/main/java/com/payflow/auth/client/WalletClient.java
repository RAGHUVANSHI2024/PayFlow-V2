package com.payflow.auth.client;

import com.payflow.auth.dto.CreateWalletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service")
public interface WalletClient {

    @PostMapping("/api/v1/wallets")
    void createWallet(@RequestBody CreateWalletRequest request);
}

