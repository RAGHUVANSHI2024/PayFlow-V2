package com.payflow.wallet.consumer;

import com.payflow.wallet.dto.CreditMoneyCommand;
import com.payflow.wallet.dto.DebitMoneyCommand;
import com.payflow.wallet.service.WalletSagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditMoneyConsumer {

    private final WalletSagaService walletSagaService;

    @KafkaListener(
            topics = "credit-money-topic",
            groupId = "wallet-saga-group"
    )
    public void consume(CreditMoneyCommand command) {
        walletSagaService.credit(command);
    }
}
