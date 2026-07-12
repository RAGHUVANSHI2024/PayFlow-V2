package com.payflow.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic notificationCreatedTopic() {
        return TopicBuilder
                .name("notification-created-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificationFailedTopic() {
        return TopicBuilder
                .name("notification-failed-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
