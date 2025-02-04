package com.ubik.formation.borrowingservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.topic.user}")
    private String user_topic;

    @Value("${spring.kafka.producer.topic.book")
    private String book_topic;

    @Bean
    public NewTopic lockUserTopic() {
        return new NewTopic(user_topic, 3, (short) 1);
    }

    @Bean
    public NewTopic bookBorrowedTopic() {
        return new NewTopic(book_topic, 3, (short) 1);
    }
}
