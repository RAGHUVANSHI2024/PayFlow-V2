package com.payflow.saga.consumer;

import com.payflow.saga.dto.MoneyCreditedEvent;
import com.payflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoneyCreditedConsumer {

    private final SagaService sagaService;

    @KafkaListener(
            topics = "money-credited-topic",
            groupId = "saga-service"
    )
    public void consume(MoneyCreditedEvent event){

        sagaService.moneyCredited(event);
    }
}
