package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByUserId(String userId);
}
