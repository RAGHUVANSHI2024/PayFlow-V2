package com.payflow.saga.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic debitMoneyTopic(){

        return TopicBuilder
                .name("debit-money-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic creditMoneyTopic(){

        return TopicBuilder
                .name("credit-money-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic sendNotificationTopic(){

        return TopicBuilder
                .name("send-notification-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic refundMoneyTopic(){

        return TopicBuilder
                .name("refund-money-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }


}
