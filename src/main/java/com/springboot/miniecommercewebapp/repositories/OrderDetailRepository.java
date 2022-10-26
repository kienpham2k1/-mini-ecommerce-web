package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderItemsEntity, Integer> {
    List<OrderItemsEntity> findByOrderId(int orderId);
    Optional<OrderItemsEntity> findByOrderIdAndProductId(int orderId, int producctId);
    boolean existsByOrderId(int orderId);
}
