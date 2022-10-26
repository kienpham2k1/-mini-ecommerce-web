package com.springboot.miniecommercewebapp.dto.request;

import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import lombok.Data;

import java.util.List;

@Data
public class CartSelected {
    private OrdersEntity newOrder;
    private List<CartsEntity> cartList;
}
