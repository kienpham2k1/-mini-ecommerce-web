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
        Optional<Integer> quantityCheck; //= productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity());
        Optional<Cart> foundCart = cartRepository.findByUserIdAndProductId(newCart.getUserId(), newCart.getProductId());
        // If is present, then update
        if (foundCart.isPresent()) {
            quantityCheck = productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity() + foundCart.get().getQuantity());
            if (quantityCheck.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update to cart successfully!", updateCartItem(foundCart.get().getCartId(), newCart, newCart.getQuantity())));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Product have not enough quantity", ""));
            }
        }
        // If is not exist, then add new
        else {
            quantityCheck = productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity());
            if (quantityCheck.isPresent()) {
                newCart.setPrice(newCart.getQuantity() * productRepository.findById(newCart.getProductId()).get().getPrice());
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update to cart successfully!", cartRepository.save(newCart)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Product have not enough quantity", ""));
            }
        }

    }

    @Override
    public ResponseEntity<ResponseObject> getCartItemsByUserId(String userId) {
        List<Cart> foundCarts = cartRepository.findByUserId(userId);
        if (!foundCarts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Successfully!", foundCarts));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found cart items!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateCartItem(int cartId, Cart updateCart, int plus) {
        Optional<Cart> foundCart = cartRepository.findByUserIdAndProductId(updateCart.getUserId(), updateCart.getProductId());
        if (foundCart.isPresent()) {
            if (productRepository.findByProductIdAndQuantity(updateCart.getProductId(), updateCart.getQuantity()).isPresent()) {
                foundCart.map(cart -> {
                    cart.setQuantity(updateCart.getQuantity() + plus);
                    cart.setPrice(productRepository.findById(cart.getProductId()).get().getPrice() * (updateCart.getQuantity() + plus));
                    return cartRepository.save(cart);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update Successfully!", foundCart));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("fail", "Product quantity is not enough", productRepository.findById(updateCart.getProductId())));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Cart!", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCartItem(int cartId) {
        boolean isExist = cartRepository.existsById(cartId);
        if (isExist) {
            cartRepository.deleteById(cartId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Cart Item!", null));
        }
    }
}
