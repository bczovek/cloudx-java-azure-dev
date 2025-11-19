package com.chtrembl.petstore.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chtrembl.petstore.product.model.Product;
import com.chtrembl.petstore.product.model.Status;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStatusIn(List<Status> statuses);
}
