package com.ubik.formation.userservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.producer.topic.user}")
    private String topic;

    @Bean
    public NewTopic userDeletionTopic() {
        return new NewTopic(topic, 3, (short) 1);
    }
}
