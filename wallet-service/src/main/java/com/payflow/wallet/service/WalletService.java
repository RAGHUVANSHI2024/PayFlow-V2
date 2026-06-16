package com.payflow.wallet.service;

import com.payflow.wallet.dto.CreateWalletRequest;

import java.math.BigDecimal;

public interface WalletService {

    void createWallet(CreateWalletRequest request);

    BigDecimal getWalletBalance(Long walletId);
}
