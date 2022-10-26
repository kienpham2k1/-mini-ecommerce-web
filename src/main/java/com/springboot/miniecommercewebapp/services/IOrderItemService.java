package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;

import java.util.List;

public interface IOrderItemService {
    List<OrderItemsEntity> getOrderItemsByOrderId(int orderId);

    OrderItemsEntity addOrderItem(int orderId, CartsEntity cartItem);

    boolean cancelOrderDetail(int orderItemId);
}
