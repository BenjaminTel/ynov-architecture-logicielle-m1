package org.example.cardservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cardservice.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CardKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardKafkaConsumer.class);

    @Autowired
    private CardService cardService;

    @KafkaListener(topics = "account-events", groupId = "card-group")
    public void consumeAccountDeletedEvent(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode event = mapper.readTree(message);

            if ("ACCOUNT_DELETED".equals(event.get("event").asText())) {
                Long id = event.get("accountId").asLong();
                LOGGER.info("Account deleted: accountId : {}", id);
                cardService.deleteCardByAccountId(id);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
