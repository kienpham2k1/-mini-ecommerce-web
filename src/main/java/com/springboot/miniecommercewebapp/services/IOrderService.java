package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.request.CartSelected;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<OrdersEntity> getAllOrders();
    Optional<OrdersEntity> getOrder(int orderId);
    OrdersEntity addOrder(List<CartsEntity> listCart);
    OrdersEntity updateOrder(int orderID, String status);
    boolean cancelOrder(int orderId);
}
