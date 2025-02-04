package com.ubik.formation.borrowingservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.producer.topic.user}")
    private String user_topic;

    @Value("${spring.kafka.producer.topic.book}")
    private String book_topic;

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookBorrowedMessage(Long bookId) {
        String event = String.format("{\"event\":\"BOOK_BORROWED\",\"bookId\":\"%s\"}", bookId);
        log.info(event);
        kafkaTemplate.send(user_topic, event);
    }

    public void sendLockUserMessage(Long userId) {
        String event = String.format("{\"event\":\"LOCK_USER\",\"userId\":\"%s\"}", userId);
        log.info(event);
        kafkaTemplate.send(book_topic, event);
    }
}
