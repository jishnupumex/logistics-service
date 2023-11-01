package com.supply.logistics.config;

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

    @Value("${spring.kafka.topic.name.OrderAvailability}")
    private String topicOrderAvailabilityName;

    @Bean
    public NewTopic OrderAvailabilityTopic() {
        return TopicBuilder.name(topicOrderAvailabilityName)
                .build();
    }
}