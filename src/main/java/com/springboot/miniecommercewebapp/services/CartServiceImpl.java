package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
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
    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseObject> addToCart(Cart newCart) {
        // Should update with product quantity
        // Check product's quantity: need to check quantity is not less than product quantity
        // Need > 0 && Need <= product Quantity
        Optional<Integer> quantityCheck = productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity());
        Optional<Cart> foundCart = cartRepository.findByUserIdAndProductId(newCart.getUserId(), newCart.getProductId());
        //When not found product or out of product quantity
        if (quantityCheck.isEmpty()
                || (productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity() + Integer.valueOf(foundCart.get().getQuantity())).isEmpty())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found Product or product have not enough quantity", ""));
        } else {
            // When cart is not create
            if (foundCart.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Add to cart successfully!", cartRepository.save(newCart)));
                //When cart already create ? Update Cart
                //quantity in cart + newCart.getQuantity()
            } else {
                return updateCartItem(newCart, Integer.valueOf(foundCart.get().getQuantity()));
            }
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
    public ResponseEntity<ResponseObject> updateCartItem(Cart updateCart, int plus) {
        Optional<Cart> foundCart = cartRepository.findByUserIdAndProductId(updateCart.getUserId(), updateCart.getProductId())
                .map(cart -> {
                    cart.setQuantity(updateCart.getQuantity() + plus);
                    return cartRepository.save(cart);
                });
        if (foundCart.isPresent()) {
            if (productRepository.findByProductIdAndQuantity(updateCart.getProductId(), updateCart.getQuantity()).isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update Successfully!", foundCart));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("fail", "Product quantity is not enough", productRepository.findById(updateCart.getProductId())));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found Cart!", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCartItem(int cartId) {
        boolean isExist = cartRepository.existsById(cartId);
        if (isExist) {
            cartRepository.deleteById(cartId);
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
