package com.payflow.wallet.service;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.enums.WalletStatus;
import com.payflow.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;

    @Override
    public void createWallet(CreateWalletRequest request) {


        Wallet wallet = Wallet.builder()
                .userId(request.getUserId())
                .walletNumber(UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,12)
                )
                .balance(BigDecimal.ZERO)
                .status(WalletStatus.ACTIVE)
                .build();

        walletRepository.save(wallet);
    }
}
