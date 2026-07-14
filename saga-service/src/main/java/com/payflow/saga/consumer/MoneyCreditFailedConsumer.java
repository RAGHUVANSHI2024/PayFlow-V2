package com.payflow.saga.consumer;

import com.payflow.saga.dto.MoneyCreditFailedEvent;
import com.payflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoneyCreditFailedConsumer {

    private final SagaService sagaService;

    @KafkaListener(
            topics = "money-credit-failed-topic",
            groupId = "saga-group"
    )
    public void consume(MoneyCreditFailedEvent event){
        sagaService.moneyCreditFailed(event);
    }
}
