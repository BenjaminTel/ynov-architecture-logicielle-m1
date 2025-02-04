package org.example.loansservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoanKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanKafkaConsumer.class);
    private final KafkaTemplate<String, String> KAFKA_TEMPLATE;
    @Value("${spring.kafka.topic.account:account-events}")
    private String topic;

    public LoanKafkaConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.KAFKA_TEMPLATE = kafkaTemplate;
    }

    public void sendAccountDeleteEvent(Long accountId) {
        String event = String.format("{\"event\":\"ACCOUNT_DELETED\",\"accountId\":\"%s\"}", accountId);
        LOGGER.info("Sending account deleted event: {}", event);
        KAFKA_TEMPLATE.send(topic, event);
    }
}
