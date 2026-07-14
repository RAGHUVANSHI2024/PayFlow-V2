package com.payflow.saga.consumer;

import com.payflow.saga.dto.RefundMoneyCommand;
import com.payflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefundMoneyConsumer {

    private final SagaService sagaService;

    @KafkaListener(
            topics = "refund-money-topic",
            groupId = "saga-group"
    )
    public void consume(RefundMoneyCommand command){
        sagaService.refund(command);
    }

}
