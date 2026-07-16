package com.payflow.wallet.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic moneyTransferTopic(){
        return TopicBuilder
                .name("money-transfer-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic moneyTransferDLT(){
        return TopicBuilder
                .name("money-transfer-topic-dlt")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic transferRequestedTopic() {

        return TopicBuilder
                .name("transfer-requested-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic moneyDebitedTopic() {

        return TopicBuilder
                .name("money-debited-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic moneyDebitFailedTopic() {

        return TopicBuilder
                .name("money-debit-failed-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic creditMoneyTopic() {

        return TopicBuilder
                .name("credit-money-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic moneyCreditedTopic() {

        return TopicBuilder
                .name("money-credited-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic moneyCreditFailedTopic() {

        return TopicBuilder
                .name("money-credit-failed-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic refundMoneyTopic() {
        return TopicBuilder
                .name("refund-money-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic moneyRefundedTopic() {
        return TopicBuilder
                .name("money-refunded-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
