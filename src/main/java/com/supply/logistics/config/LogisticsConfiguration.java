package com.supply.logistics.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class LogisticsConfiguration {

    @Value("${spring.kafka.topic.name.OrderFulfillment}")
    private String topicOrderFulfillmentName;

    @Bean
    public NewTopic OrderFulfillmentTopic() {
        return TopicBuilder.name(topicOrderFulfillmentName)
                .build();
    }

    @Value("${spring.kafka.topic.name.OrderFulfillmentBySupplier")
    private String topicOrderFulfillmentSupplier;

    @Bean
    public NewTopic OrderFulfillmentBySupplierTopic() {
        return TopicBuilder.name(topicOrderFulfillmentSupplier)
                .build();
    }

    @Value("${spring.kafka.topic.name.OrderAvailability}")
    private String topicOrderAvailabilityName;

    @Bean
    public NewTopic OrderAvailabilityTopic() {
        return TopicBuilder.name(topicOrderAvailabilityName)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Ignore unknown properties during deserialization
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}