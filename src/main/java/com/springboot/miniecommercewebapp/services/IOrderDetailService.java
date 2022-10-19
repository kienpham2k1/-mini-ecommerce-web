package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.OrderDetail;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderDetailService {
    ResponseEntity<ResponseObject> getOrderItemsByOrderId(int orderId);

    ResponseEntity<ResponseObject> addOrderItem(OrderDetail newOrderItem);
}
