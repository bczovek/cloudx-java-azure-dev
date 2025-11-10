package com.chtrembl.petstore.order.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chtrembl.petstore.order.model.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemReserverService {

    private final RestTemplate restTemplate;

    @Value("${petstore.service.orderitemreserver.url}")
    private String orderItemReserverUrl;

    public void sendOrderToReserver(Order order) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                orderItemReserverUrl, HttpMethod.POST, new HttpEntity<>(order),
                Void.class);
            
                HttpStatusCode statusCode = response.getStatusCode();
                if(!statusCode.is2xxSuccessful()) {
                    log.error("Function returned non-200 status code: {}", statusCode.value());
                }
                log.info("Reserve order item function call successful!");
        } catch (Exception e) {
            log.error("Failed to call Order Item Reserver Function: {}", e.getMessage(), e);
        }
    }
}
