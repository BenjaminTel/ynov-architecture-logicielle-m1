package com.ubik.formation.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Value("${spring.kafka.producer.topic.user}")
    private String topic;

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserDeletedMessage(Long userId) {
        String event = String.format("{\"event\":\"USER_DELETED\",\"userId\":\"%s\"}", userId);
        log.info(event);
        kafkaTemplate.send(topic, event);
    }
}
