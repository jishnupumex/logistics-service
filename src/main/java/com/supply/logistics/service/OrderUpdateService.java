package com.supply.logistics.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supply.logistics.entity.Inventory;
import com.supply.logistics.entity.OrderRequest;
import com.supply.logistics.entity.OrderStatus;
import com.supply.logistics.entity.UserOrders;
import com.supply.logistics.repo.InventoryRepo;
import com.supply.logistics.repo.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderUpdateService {

    private final UserOrderRepository userOrderRepository;
    private final ObjectMapper objectMapper;
    private final InventoryRepo inventoryRepo;

    @KafkaListener(topics = "${spring.kafka.topic.name.OrderFulfillmentBySupplier}", groupId = "OrderFulfillmentBySupplierGroup")
    public void consumeOrderFulfillmentBySupplierTopic(String orderRequestAsString) throws JsonProcessingException {
        OrderRequest orderRequest = objectMapper.readValue(orderRequestAsString, OrderRequest.class);
        log.info("Seller update received -> {}", orderRequest);
        log.info("Prod ID -> {} , Required Prod Qty {} of OrderID - {}", orderRequest.getProdNumber(), orderRequest.getRequiredQty(), orderRequest.getOrderId());

        // Check if orderId is not null before calling findById
        if (orderRequest.getOrderId() != null) {
            Optional<UserOrders> optionalUserOrder = userOrderRepository.findById(orderRequest.getOrderId());

            if (optionalUserOrder.isPresent()) {
                UserOrders existingUserOrder = optionalUserOrder.get();
                existingUserOrder.setOrderStatus(OrderStatus.AVAILABLE);
                userOrderRepository.save(existingUserOrder);
                log.info("Order Status: {}", existingUserOrder.getOrderStatus());
                Optional<Inventory> optionalInventory = inventoryRepo.findByProdId(Math.toIntExact(existingUserOrder.getProdId()));

                if (optionalInventory.isPresent()) {
                    Inventory existingInventory = optionalInventory.get();
                    log.info("Inventory Status of prod id {} before delivery: {} ", existingInventory.getProdId(), existingInventory.getProdQty());

                    // Update the prodQty based on the difference
                    existingInventory.setProdQty(existingInventory.getProdQty() - existingUserOrder.getProdQty());

                    // Save the updated Inventory entry
                    inventoryRepo.save(existingInventory);

                    log.info("Inventory Status of prod id {} after delivery prod qty: {} ", existingInventory.getProdId(), existingInventory.getProdQty());

                    // Call the method to update orderStatus to "Delivered"
                    updateOrderStatusToDelivered(existingUserOrder);
                } else {
                    log.error("Inventory not found for prodId: {}", existingUserOrder.getProdId());
                }
            } else {
                log.info("Product not available");
            }
        } else {
            log.error("Order ID is null.");
        }
    }

    private void updateOrderStatusToDelivered(UserOrders userOrders) {
        userOrders.setOrderStatus(OrderStatus.DELIVERED);
        userOrderRepository.save(userOrders);
        log.info("Order Status is {} for OrderID {} ", userOrders.getOrderStatus(),userOrders.getUserOrderId());
    }
}
