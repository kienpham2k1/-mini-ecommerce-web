package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemsEntity, Integer> {
    List<OrderItemsEntity> findByOrderId(int orderId);
}
