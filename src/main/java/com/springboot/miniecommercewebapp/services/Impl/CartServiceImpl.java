package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.services.ICartService;
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
    public ResponseEntity<SuccessResponse> getCartItemsByUserId(String userId) {
        List<CartsEntity> foundCarts = cartRepository.findByUserId(userId);
        if (!foundCarts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Successfully!", foundCarts));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found cart items!", null));
        }
    }

    @Override
    public ResponseEntity<SuccessResponse> addToCart(CartsEntity newCart) {
        // Should update with product quantity
        // Check product's quantity: need to check quantity is not less than product quantity
        // Need > 0 && Need <= product Quantity
        Optional<ProductsEntity> quantityCheck;
        Optional<CartsEntity> foundCart = cartRepository.findByUserIdAndProductId(newCart.getUserId(), newCart.getProductId());
        // If is present, then update
        if (foundCart.isPresent()) {
            quantityCheck = productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity() + foundCart.get().getQuantity());
            if (quantityCheck.isPresent()) {
//                Optional<Cart> updateCart = ;
                return ResponseEntity.status(HttpStatus.OK).body(
                        new SuccessResponse("ok", "Update to cart successfully!",
                                updateCartItem(foundCart.get().getCartId(), foundCart.get(), newCart.getQuantity())));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new SuccessResponse("failed", "Product have not enough quantity", ""));
            }
        }
        // If is not exist, then add new
        else {
            quantityCheck = productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity());
            if (quantityCheck.isPresent()) {
                newCart.setPrice(newCart.getQuantity() * productRepository.findById(newCart.getProductId()).get().getPrice());
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Update to cart successfully!", cartRepository.save(newCart)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Product have not enough quantity", ""));
            }
        }

    }


    @Override
    public ResponseEntity<SuccessResponse> updateCartItem(int cartId, CartsEntity updateCart, int quantityPlus) {
        Optional<CartsEntity> foundCart = cartRepository.findByUserIdAndProductId(updateCart.getUserId(), updateCart.getProductId());
        if (foundCart.isPresent()) {
            if (productRepository.findByProductIdAndQuantity(updateCart.getProductId(), updateCart.getQuantity()).isPresent()) {
                foundCart.map(cart -> {
                    cart.setQuantity(updateCart.getQuantity() + quantityPlus);
                    cart.setPrice(productRepository.findById(cart.getProductId()).get().getPrice() * (updateCart.getQuantity()));
                    return cartRepository.save(cart);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Update Successfully!", foundCart));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new SuccessResponse("fail", "Product quantity is not enough", productRepository.findById(updateCart.getProductId())));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found Cart!", ""));
        }
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteCartItem(int cartId) {
        boolean isExist = cartRepository.existsById(cartId);
        if (isExist) {
            cartRepository.deleteById(cartId);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Delete successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found Cart Item!", null));
        }
    }
}
