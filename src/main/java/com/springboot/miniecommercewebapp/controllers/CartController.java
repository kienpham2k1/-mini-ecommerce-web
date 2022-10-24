package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.CartEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.services.cartService.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cart")
public class CartController {
    @Autowired
    ICartService iCartService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllCartItem(@RequestParam(name = "userId") String userId) {
        return iCartService.getCartItemsByUserId(userId);
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> addNewCart(@RequestBody @Valid CartEntity newCart) {
        return iCartService.addToCart(newCart);
    }

    @PutMapping("{cartId}")
    ResponseEntity<ResponseObject> updateCart(@PathVariable int cartId, @RequestBody CartEntity updateCart) {
        return iCartService.updateCartItem(cartId, updateCart, 0);
    }

    @DeleteMapping("/{cartId}")
    ResponseEntity<ResponseObject> deleteCartItem(@PathVariable int cartId) {
        return iCartService.deleteCartItem(cartId);
    }
}
