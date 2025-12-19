package com.chtrembl.petstore.order.service;

import org.springframework.stereotype.Service;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.chtrembl.petstore.order.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceBusService {

    private final ServiceBusSenderClient client;
    private final ObjectMapper objectMapper;

    public void sendOrder(Order order) {
        try {
            client.sendMessage(new ServiceBusMessage(objectMapper.writeValueAsString(order)));
            log.info("Order item sent to Service Bus queue!");
        } catch (Exception e) {
            log.error("Failed to send Order to Service Bus Queue: {}", e.getMessage(), e);
        }
    }
}
