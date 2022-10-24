package com.springboot.miniecommercewebapp.dto;

import com.springboot.miniecommercewebapp.models.CartEntity;
import com.springboot.miniecommercewebapp.models.OrderEntity;
import lombok.Data;

import java.util.List;

@Data
public class CartSelected {
    private OrderEntity newOrder;
    private List<CartEntity> cartList;
}
