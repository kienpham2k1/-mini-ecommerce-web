package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    public ResponseEntity<ResponseObject> addToCart(Cart newCart) {
        Optional<Cart> foundCart = cartRepository.findByUserIdAndProductId(newCart.getUserId(), newCart.getProductId());
        if (foundCart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Successfully!", cartRepository.save(newCart)));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Already have this cart!", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getCartItemsByUserId(String userId) {
        List<Cart> foundCarts = cartRepository.findByUserId(
                userId);
        if (!foundCarts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Successfully!", foundCarts));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found cart items!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateCartItem(Cart updateCart) {
        Optional<Cart> foundCart = cartRepository.findByUserIdAndProductId(updateCart.getUserId(), updateCart.getProductId())
                .map(cart -> {
                    cart.setQuantity(updateCart.getQuantity());
                    return cartRepository.save(cart);
                });
        if (foundCart.isPresent()) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Successfully!", foundCart));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Not found Cart!", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCartItem(int cartId) {
        boolean isExist = cartRepository.existsById(cartId);
        if (isExist) {
            cartRepository.deleteById(cartId);
            // After delte must return product quantity to server
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully!", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found Cart Item!", null)
            );
        }
    }
}
