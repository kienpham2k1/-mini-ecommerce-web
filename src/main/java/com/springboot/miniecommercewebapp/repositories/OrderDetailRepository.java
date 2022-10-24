package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
    List<OrderDetailEntity> findByOrderId(int orderId);
    Optional<OrderDetailEntity> findByOrderIdAndProductId(int orderId, int producctId);
    boolean existsByOrderId(int orderId);
}
