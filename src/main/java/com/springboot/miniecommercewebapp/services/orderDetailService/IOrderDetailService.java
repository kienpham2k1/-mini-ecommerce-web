package com.springboot.miniecommercewebapp.services.orderDetailService;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.OrderDetail;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderDetailService {
    ResponseEntity<ResponseObject> getOrderItemsByOrderId(int orderId);

    ResponseEntity<ResponseObject> addOrderItem(int orderId, Cart cartItem);

    ResponseEntity<ResponseObject> cancelOrderDetail(OrderDetail cancelOrderDetail);
}
