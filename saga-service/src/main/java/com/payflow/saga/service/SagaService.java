package com.payflow.saga.service;

import com.payflow.saga.dto.*;

public interface SagaService {

    void processTransfer(TransferRequestedEvent event);

    void moneyDebited(MoneyDebitedEvent event);

    void moneyCredited(MoneyCreditedEvent event);

    void moneyCreditFailed(MoneyCreditFailedEvent event);

    void refund(RefundMoneyCommand command);
}
