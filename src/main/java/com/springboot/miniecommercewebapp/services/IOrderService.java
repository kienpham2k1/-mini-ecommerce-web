package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.request.CartSelected;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<SuccessResponse> getAllOrders(String userId);
    ResponseEntity<SuccessResponse> getOrder(int orderId);
    ResponseEntity<SuccessResponse> addOrder(CartSelected newCartSelected);
    ResponseEntity<SuccessResponse> updateOrder(int orderID, int status);
    ResponseEntity<SuccessResponse> cancelOrder(int orderId);
}
