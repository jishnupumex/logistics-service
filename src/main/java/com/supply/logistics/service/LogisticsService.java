package com.supply.logistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supply.logistics.entity.OrderStatus;
import com.supply.logistics.entity.UserOrders;
import com.supply.logistics.repo.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogisticsService {
    private final UserOrderRepository userOrderRepository;
    private final OrderUpdateService orderUpdateService;
    private final KafkaTemplate<String, UserOrders> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic.name.OrderFulfillment}", groupId = "${spring.kafka.consumer.group-id.OrderFulfillment}")
    public void consumeOrderFulfillmentTopic(String userOrdersAsString) throws JsonProcessingException {
        UserOrders userOrders = objectMapper.readValue(userOrdersAsString, UserOrders.class);

        userOrders.setOrderStatus(OrderStatus.DELIVERED);
        userOrderRepository.save(userOrders);
        log.info("Order Status -> {}", userOrders.getOrderStatus());
    }
}