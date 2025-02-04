package com.ubik.formation.userservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Value("${spring.kafka.consumer.topic.borrowing}")
    private String topic;

    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final UserService userService;

    public KafkaConsumerService(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "lock-user", groupId = "tp_group")
    public void consumeMaxBorrowingEvent(String message) {
        logger.info("Received lock user event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("LOCK_USER".equals(event.get("event").asText())) {
                Long userId = event.get("userId").asLong();
                logger.info("Processing lock user for userID: {}", userId);
                userService.lockUser(userId);
            }
        } catch (Exception e) {
            logger.error("Error processing lock user event", e);
        }
    }
}
