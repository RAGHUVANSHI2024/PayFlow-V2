package com.payflow.wallet.consumer;

import com.payflow.wallet.dto.RefundMoneyCommand;
import com.payflow.wallet.service.WalletSagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundMoneyConsumer {

    private final WalletSagaService walletSagaService;

    @KafkaListener(
            topics = "refund-money-topic",
            groupId = "wallet-saga-group"
    )
    public void consume(RefundMoneyCommand command){

        walletSagaService.refund(command);

    }

}