package com.chtrembl.petstore.order.repository;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.chtrembl.petstore.order.model.Order;

@Repository
public interface OrderRepository extends CosmosRepository<Order, String> {

}
