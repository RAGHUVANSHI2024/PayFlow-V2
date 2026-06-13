package com.payflow.wallet.service;

import com.payflow.wallet.dto.TransferMoneyRequest;

public interface TransactionService {

    void transfer(TransferMoneyRequest request);
}
