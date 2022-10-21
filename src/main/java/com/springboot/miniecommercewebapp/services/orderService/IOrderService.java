package com.springboot.miniecommercewebapp.services.orderService;

import com.springboot.miniecommercewebapp.dto.CartSelected;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<ResponseObject> getAllOrders(String userId);
    ResponseEntity<ResponseObject> getOrder(int orderId);
    ResponseEntity<ResponseObject> addOrder(CartSelected newCartSelected);
    ResponseEntity<ResponseObject> updateOrder(int orderID, int status);
    ResponseEntity<ResponseObject> cancelOrder(int orderId);
}
