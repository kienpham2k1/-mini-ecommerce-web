package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CartsEntity> getCartItemsByUserId(String userId) {
        List<CartsEntity> foundCarts = cartRepository.findByUserId(userId);
        if (!foundCarts.isEmpty()) {
            return foundCarts;
        }
        throw new NotFoundException("Not found carts");
    }

    @Override
    public CartsEntity addToCart(CartsEntity newCart) {
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
                return updateCartItem(foundCart.get().getCartId(), foundCart.get(), newCart.getQuantity());
            }
            throw new NotFoundException("Not enough quantity");
        }
        // If is not exist, then add new
        else {
            quantityCheck = productRepository.findByProductIdAndQuantity(newCart.getProductId(), newCart.getQuantity());
            if (quantityCheck.isPresent()) {
                newCart.setPrice(newCart.getQuantity() * productRepository.findById(newCart.getProductId()).get().getPrice());
                return cartRepository.save(newCart);
            }
            throw new NotFoundException("Not enough quantity");
        }
    }
    @Override
    public CartsEntity updateCartItem(int cartId, CartsEntity updateCart, int quantityPlus) {
        Optional<CartsEntity> foundCart = cartRepository.findByUserIdAndProductId(updateCart.getUserId(), updateCart.getProductId());
        if (foundCart.isPresent()) {
            if (productRepository.findByProductIdAndQuantity(updateCart.getProductId(), updateCart.getQuantity()).isPresent()) {
                foundCart.map(cart -> {
                    cart.setQuantity(updateCart.getQuantity() + quantityPlus);
                    cart.setPrice(productRepository.findById(cart.getProductId()).get().getPrice() * (updateCart.getQuantity()));
                    return cart;
                });
                return cartRepository.save(foundCart.get());
            }
            throw new NotFoundException("Not enough quantity");
        }
        throw new NotFoundException("Not found cart item");
    }

    @Override
    public boolean deleteCartItem(int cartId) {
        boolean isExist = cartRepository.existsById(cartId);
        if (isExist) {
            cartRepository.deleteById(cartId);
            return true;
        }
        throw new NotFoundException("Not found to delete");
    }
}
