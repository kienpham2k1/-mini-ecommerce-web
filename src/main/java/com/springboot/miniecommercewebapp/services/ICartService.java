package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseEntity<SuccessResponse> addToCart(CartsEntity newCart);
    ResponseEntity<SuccessResponse> getCartItemsByUserId(String userId);
    ResponseEntity<SuccessResponse> updateCartItem(int cartId, CartsEntity updateCart, int plus);
    ResponseEntity<SuccessResponse> deleteCartItem(int cartId);
}
