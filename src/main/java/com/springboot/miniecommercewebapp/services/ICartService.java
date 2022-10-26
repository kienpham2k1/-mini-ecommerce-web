package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICartService {
    List<CartsEntity> getCartItemsByUserId(String userId);
    CartsEntity addToCart(CartsEntity newCart);
    CartsEntity updateCartItem(int cartId, CartsEntity updateCart, int plus);
    boolean deleteCartItem(int cartId);
}
