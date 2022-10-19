package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseEntity<ResponseObject> addToCart(Cart newCart);
    ResponseEntity<ResponseObject> getCartItemsByUserId(String userId);
    ResponseEntity<ResponseObject> updateCartItem(int cartId, Cart updateCart,int plus);
    ResponseEntity<ResponseObject> deleteCartItem(int cartId);
}
