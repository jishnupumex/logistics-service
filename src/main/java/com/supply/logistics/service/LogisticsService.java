package com.supply.logistics.service;

import com.scm.UserOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class LogisticsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsService.class);

    @Autowired
    private OrderUpdateService orderUpdateService; // Add @Autowired annotation here

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LogisticsService(KafkaTemplate<String, String> kafkaTemplate, OrderUpdateService orderUpdateService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderUpdateService = orderUpdateService; // Inject OrderUpdateService here
    }

    @KafkaListener(topics = "${spring.kafka.topic.name.OrderFulfillment}", groupId = "${spring.kafka.consumer.group-id.OrderFulfillment}")
    public void consumeOrderFulfillmentTopic(String message) {
        LOGGER.info("Received message from OrderFulfillment topic: {} and ready for be shipped", message);
        orderUpdateService.sendProductShippedMessage();
    }
}
