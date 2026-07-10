package com.payflow.wallet.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.wallet.dto.MoneyTransferredEvent;
import com.payflow.wallet.entity.OutboxEvent;
import com.payflow.wallet.enums.OutboxStatus;
import com.payflow.wallet.kafka.KafkaProducerService;
import com.payflow.wallet.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;

    private final KafkaProducerService kafkaProducerService;

    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvent(){

        List<OutboxEvent> events = outboxRepository.findByStatus(OutboxStatus.PENDING);

        if (events.isEmpty()){
            return;
        }

        log.info("Found {} pending events .",events);

        events.forEach(outboxEvent -> {
            try {

                MoneyTransferredEvent event =
                        objectMapper.readValue(
                                outboxEvent.getPayload(),
                                MoneyTransferredEvent.class
                        );

                kafkaProducerService.sendMoneyTransferredEvent(event);

                outboxEvent.setStatus(OutboxStatus.PUBLISHED);
                outboxEvent.setPublishedAt(LocalDateTime.now());

                outboxRepository.save(outboxEvent);

                log.info("Published Event : {}",outboxEvent.getEventId());

            }catch (Exception e){
                log.error("Failed to publish Event : {}", outboxEvent.getEventId(), e);

                outboxEvent.setStatus(OutboxStatus.PENDING);

                outboxRepository.save(outboxEvent);
            }
        });

    }
}
