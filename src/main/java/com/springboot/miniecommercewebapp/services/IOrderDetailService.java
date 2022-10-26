package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import org.springframework.http.ResponseEntity;

public interface IOrderDetailService {
    ResponseEntity<SuccessResponse> getOrderItemsByOrderId(int orderId);

    ResponseEntity<SuccessResponse> addOrderItem(int orderId, CartsEntity cartItem);

    ResponseEntity<SuccessResponse> cancelOrderDetail(OrderItemsEntity cancelOrderDetail);
}
