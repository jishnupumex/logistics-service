package com.supply.logistics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderUpdateService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderUpdateService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendProductShippedMessage() {
        String message = "Product shipped";
        kafkaTemplate.send("OrderUpdate", message);
        LOGGER.info("{} to User and Product Out for Delivery",message);
        return message;
    }
}
