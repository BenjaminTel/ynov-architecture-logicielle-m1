package com.ubik.formation.bookservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.producer.topic.book}")
    private String topic;

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookDeletedMessage(Long bookId) {
        String event = String.format("{\"event\":\"BOOK_DELETED\",\"bookId\":\"%s\"}", bookId);
        log.info(event);
        kafkaTemplate.send(topic, event);
    }
}