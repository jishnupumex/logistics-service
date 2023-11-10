package com.supply.logistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supply.logistics.entity.OrderRequest;
import com.supply.logistics.entity.OrderStatus;
import com.supply.logistics.entity.UserOrders;
import com.supply.logistics.repo.UserOrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderUpdateService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserOrderRepository userOrderRepository;
    private final ObjectMapper objectMapper;
//    @KafkaListener(topics = "${spring.kafka.topic.name.OrderFulfillmentBySupplier}", groupId = "OrderFulfillmentBySupplierGroup")
//    public void consumeOrderFulfillmentBySupplierTopic(String  orderRequestAsString) throws JsonProcessingException {
//        OrderRequest orderRequest = objectMapper.readValue(orderRequestAsString, OrderRequest.class);
//log.info(String.valueOf(orderRequest.getOrderId()));
//        Optional<UserOrders> optionalUserOrder = userOrderRepository.findById(orderRequest.getOrderId());
//
//        if (optionalUserOrder.isPresent()) {
//            UserOrders existingUserOrder = optionalUserOrder.get();
//            existingUserOrder.setOrderStatus(OrderStatus.SHIPPED);
//            userOrderRepository.save(existingUserOrder);
//            log.info("Order Status -> {}", existingUserOrder.getOrderStatus());
//        } else {
//            log.info("Product not available");
//        }
//    }
}
