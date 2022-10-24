package com.springboot.miniecommercewebapp.services.orderDetailService;

import com.springboot.miniecommercewebapp.models.CartEntity;
import com.springboot.miniecommercewebapp.models.OrderDetailEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderDetailService {
    ResponseEntity<ResponseObject> getOrderItemsByOrderId(int orderId);

    ResponseEntity<ResponseObject> addOrderItem(int orderId, CartEntity cartItem);

    ResponseEntity<ResponseObject> cancelOrderDetail(OrderDetailEntity cancelOrderDetail);
}
