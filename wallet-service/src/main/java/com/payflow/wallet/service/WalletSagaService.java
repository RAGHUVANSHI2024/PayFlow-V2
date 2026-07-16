package com.payflow.wallet.service;

import com.payflow.wallet.dto.CreditMoneyCommand;
import com.payflow.wallet.dto.DebitMoneyCommand;
import com.payflow.wallet.dto.RefundMoneyCommand;

public interface WalletSagaService {

    void debit(DebitMoneyCommand command);
    void credit(CreditMoneyCommand command);
    void refund(RefundMoneyCommand command);
}
