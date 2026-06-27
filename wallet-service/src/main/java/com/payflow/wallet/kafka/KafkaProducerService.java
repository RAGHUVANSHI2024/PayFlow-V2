package com.payflow.wallet.kafka;

import com.payflow.wallet.dto.MoneyTransferredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, MoneyTransferredEvent> kafkaTemplate;

    private static final String MONEY_TRANSFER = "money-transfer-topic";

    public void sendMoneyTransferredEvent(MoneyTransferredEvent event) {

        kafkaTemplate.send(MONEY_TRANSFER, event);

        log.info("MoneyTransferredEvent publish : {}", event);
    }
}