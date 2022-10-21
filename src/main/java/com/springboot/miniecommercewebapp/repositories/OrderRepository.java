package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(String userId);
}
