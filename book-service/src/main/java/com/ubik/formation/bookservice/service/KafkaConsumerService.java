package com.ubik.formation.bookservice.service;

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
    private final BookService bookService;

    public KafkaConsumerService(BookService bookService) {
        this.bookService = bookService;
    }

    @KafkaListener(topics = "borrowed-book", groupId = "tp_group")
    public void consumeBorrowingBookEvent(String message) {
        logger.info("Received borrowing event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BOOK_BORROWED".equals(event.get("event").asText())) {
                Long bookId = event.get("bookId").asLong();
                logger.info("Processing borrowed book for bookID: {}", bookId);
                bookService.borrowBook(bookId);
            }
        } catch (Exception e) {
            logger.error("Error processing book borrowed event", e);
        }
    }
}
