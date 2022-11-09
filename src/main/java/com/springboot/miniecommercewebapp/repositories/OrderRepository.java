package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity, Integer> {
    List<OrdersEntity> findByUserIdOrderByOrderDateDesc(String userId);
}
