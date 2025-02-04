package com.ubik.formation.borrowingservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Value("${spring.kafka.consumer.topic.book}")
    private String book_topic;

    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final BorrowingService borrowingService;

    public KafkaConsumerService(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @KafkaListener(topics = "book-deleted", groupId = "tp_group")
    public void consumeBookDeletedEvent(String message) {
        logger.info("Received book deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BOOK_DELETED".equals(event.get("event").asText())) {
                Long bookId = event.get("bookId").asLong();
                logger.info("Processing borrowing deletion for bookID: {}", bookId);
                borrowingService.deleteByBookId(bookId);
            }
        } catch (Exception e) {
            logger.error("Error processing borrowing delete event", e);
        }
    }

    @KafkaListener(topics = "user-deleted", groupId = "tp_group")
    public void consumeUserDeletedEvent(String message) {
        logger.info("Received user deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("USER_DELETED".equals(event.get("event").asText())) {
                Long userId = event.get("userId").asLong();
                logger.info("Processing borrowing deletion for userId: {}", userId);
                borrowingService.deleteByUserId(userId);
            }
        } catch (Exception e) {
            logger.error("Error processing borrowing delete event", e);
        }
    }
}
