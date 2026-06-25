package com.payflow.user.client;

import com.payflow.user.dto.CreateWalletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="WALLET-SERVICE")
public interface WalletServiceClient {

    /*@PostMapping("/api/v1/wallets")
    void createWallet(@RequestBody CreateWalletRequest request);*/
}
