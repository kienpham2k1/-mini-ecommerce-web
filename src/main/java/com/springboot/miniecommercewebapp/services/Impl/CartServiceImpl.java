package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<CartsEntity> getCartItemsByUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<CartsEntity> foundCarts = cartRepository.findByUserId(auth.getName());
        if (!foundCarts.isEmpty()) {
            return foundCarts;
        }
        throw new NotFoundException("Not found carts");
    }

    @Override
    public CartsEntity addToCart(CartsEntity newCart) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        newCart.setUserId(auth.getName());
        // Should update with product quantity
        // Check product's quantity: need to check quantity is not less than product quantity
        // Need > 0 && Need <= product Quantity
        Optional<ProductsEntity> quantityCheck;
        Optional<CartsEntity> foundCart = cartRepository.findByUserIdAndProductId(newCart.getUserId(), newCart.getProductId());
        // If is present, then update
        if (foundCart.isPresent()) {
            quantityCheck = productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(newCart.getProductId(), newCart.getQuantity() + foundCart.get().getQuantity());
            if (quantityCheck.isPresent()) {
//                Optional<Cart> updateCart = ;
                return updateCartItem(foundCart.get().getCartId(), foundCart.get(), newCart.getQuantity());
            }
            throw new NotFoundException("Not enough quantity");
        }
        // If is not exist, then add new
        else {
            quantityCheck = productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(newCart.getProductId(), newCart.getQuantity());
            if (quantityCheck.isPresent()) {
                newCart.setPrice(newCart.getQuantity() * productRepository.findById(newCart.getProductId()).get().getPrice());
                return cartRepository.save(newCart);
            }
            throw new NotFoundException("Not enough quantity or product is not in stock");
        }
    }

    @Override
    public CartsEntity updateCartItem(int cartId, CartsEntity updateCart, int quantityPlus) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equalsIgnoreCase(updateCart.getUserId())) {
            throw new AccessDeniedException("You dont have permistion to do this");
        }
        // newCart.setUserId(auth.getName());
        Optional<CartsEntity> foundCart = cartRepository.findByUserIdAndProductId(updateCart.getUserId(), updateCart.getProductId());
        if (foundCart.isPresent()) {
            if (productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(updateCart.getProductId(), updateCart.getQuantity()).isPresent()) {
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
        Optional<CartsEntity> foundCart = cartRepository.findById(cartId);
        if (foundCart.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getName().equalsIgnoreCase(foundCart.get().getUserId())) {
                throw new AccessDeniedException("You dont have permistion to do this");
            }
            cartRepository.deleteById(cartId);
            return true;
        }
        throw new NotFoundException("Not found to delete");
    }
}
