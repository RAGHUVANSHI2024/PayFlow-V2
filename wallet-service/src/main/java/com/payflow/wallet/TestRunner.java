package com.payflow.wallet;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final WalletService walletService;

    @Override
    public void run(String... args) throws Exception {

        /*CreateWalletRequest request = CreateWalletRequest.builder()
                        .userId(2L)
                                .build();

        walletService.createWallet(request);*/
    }
}
