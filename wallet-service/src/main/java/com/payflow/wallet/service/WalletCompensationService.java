package com.payflow.wallet.service;

import com.payflow.wallet.dto.NotificationFailedEvent;

public interface WalletCompensationService {

    void refund(NotificationFailedEvent event);
}
