package com.payflow.saga.consumer;

import com.payflow.saga.dto.TransferRequestedEvent;
import com.payflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransferRequestedConsumer {

     private final SagaService sagaService;

    @KafkaListener(
            topics = "transfer-requested-topic",
            groupId = "saga-group"
    )
    public void consume(TransferRequestedEvent event){

        log.info("Transfer Requested : {}", event.getEventId());

        sagaService.processTransfer(event);
    }
}
