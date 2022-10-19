package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
