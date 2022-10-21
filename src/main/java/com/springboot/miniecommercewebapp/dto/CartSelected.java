package com.springboot.miniecommercewebapp.dto;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.Order;
import lombok.Data;

import java.util.List;

@Data
public class CartSelected {
    private Order newOrder;
    private List<Cart> cartList;
}
