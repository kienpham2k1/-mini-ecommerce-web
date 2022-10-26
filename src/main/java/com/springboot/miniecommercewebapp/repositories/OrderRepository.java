package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity, Integer> {
    List<OrdersEntity> findByUserId(String userId);
}
