package com.payflow.wallet.service;

import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {

    void transfer(TransferMoneyRequest request);

    Page<Transaction> getHistory(Long walletId, int page, int size);
}
