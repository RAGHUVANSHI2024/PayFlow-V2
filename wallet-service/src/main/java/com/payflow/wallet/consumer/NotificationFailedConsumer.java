package com.payflow.wallet.consumer;

import com.payflow.wallet.dto.NotificationFailedEvent;
import com.payflow.wallet.service.WalletCompensationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationFailedConsumer {

    private final WalletCompensationService walletCompensationService;

    @KafkaListener(
            topics = "notification-failed-topic",
            groupId = "wallet-compensation-group"
    )
    public void consume(NotificationFailedEvent event){

        log.info(
                "Notification failed received : {}",
                event.getOriginalEventId()
        );

        walletCompensationService.refund(event);
    }
}
