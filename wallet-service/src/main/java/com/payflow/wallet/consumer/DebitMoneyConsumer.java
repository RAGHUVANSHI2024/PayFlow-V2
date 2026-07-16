package com.payflow.wallet.consumer;

import com.payflow.wallet.dto.DebitMoneyCommand;
import com.payflow.wallet.service.WalletSagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DebitMoneyConsumer {
    private final WalletSagaService walletSagaService;


    @KafkaListener(
            topics = "debit-money-topic",
            groupId = "wallet-saga-group"
    )
    public void consume(DebitMoneyCommand command){
        walletSagaService.debit(command);
    }
}
