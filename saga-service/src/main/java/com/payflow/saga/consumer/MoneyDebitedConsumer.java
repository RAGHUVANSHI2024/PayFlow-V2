package com.payflow.saga.consumer;

import com.payflow.saga.dto.MoneyDebitedEvent;
import com.payflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoneyDebitedConsumer {

    private final SagaService sagaService;

    @KafkaListener(
            topics = "money-debited-topic",
            groupId = "saga-group"
    )
    public void consume(MoneyDebitedEvent event){

        log.info("");
        sagaService.moneyDebited(event);
    }
}
