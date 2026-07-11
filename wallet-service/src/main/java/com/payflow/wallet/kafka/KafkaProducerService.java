package com.payflow.wallet.kafka;

import com.payflow.wallet.dto.MoneyTransferredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, MoneyTransferredEvent> kafkaTemplate;

    private static final String MONEY_TRANSFER = "money-transfer-topic";

    public CompletableFuture<SendResult<String, MoneyTransferredEvent>>
    sendMoneyTransferredEvent(MoneyTransferredEvent event) {

        return kafkaTemplate.send(
                MONEY_TRANSFER,
                event.getSenderUserId().toString(),
                event
        );
    }
}