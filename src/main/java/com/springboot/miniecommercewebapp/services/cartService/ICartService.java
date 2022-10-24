package com.springboot.miniecommercewebapp.services.cartService;

import com.springboot.miniecommercewebapp.models.CartEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseEntity<ResponseObject> addToCart(CartEntity newCart);
    ResponseEntity<ResponseObject> getCartItemsByUserId(String userId);
    ResponseEntity<ResponseObject> updateCartItem(int cartId, CartEntity updateCart, int plus);
    ResponseEntity<ResponseObject> deleteCartItem(int cartId);
}
