package com.payflow.saga.consumer;

import com.payflow.saga.dto.MoneyRefundedEvent;
import com.payflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MoneyRefundedConsumer {

    private final SagaService sagaService;

    @KafkaListener(
            topics = "money-refunded-topic",
            groupId = "saga-group"
    )
    public void consume(MoneyRefundedEvent event){

        sagaService.moneyRefunded(event);

    }

}
