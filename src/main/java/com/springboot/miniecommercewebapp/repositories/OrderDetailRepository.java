package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(int orderId);
    Optional<OrderDetail> findByOrderIdAndProductId(int orderId, int producctId);
    boolean existsByOrderId(int orderId);
}
