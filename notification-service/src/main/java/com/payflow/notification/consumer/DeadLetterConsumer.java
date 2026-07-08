package com.payflow.notification.consumer;

import com.payflow.notification.dto.MoneyTransferredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeadLetterConsumer {

    @KafkaListener(
            topics = "money-transfer-topic.DLT",
            groupId = "notification-dlt-group"
    )
    public void consumeDeadLetter(MoneyTransferredEvent event){

        log.error("DLT Event Received {}", event);
    }
}
