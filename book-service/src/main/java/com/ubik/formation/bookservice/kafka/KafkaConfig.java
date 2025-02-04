package com.ubik.formation.bookservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.topic.book}")
    private String topic;

    @Bean
    public NewTopic bookDeletionTopic() {
        return new NewTopic(topic, 3, (short) 1);
    }
}