package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cart")
public class CartController {
    @Autowired
    ICartService iCartService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllCartItem(@RequestParam(name = "userId") String userId)
    {
        return iCartService.getCartItemsByUserId(userId);
    }
    @PostMapping("")
    ResponseEntity<ResponseObject> addNewCart(@RequestBody Cart newCart){
        return iCartService.addToCart(newCart);
    }
    @PutMapping("")
    ResponseEntity<ResponseObject> updateCart(@RequestBody Cart updateCart) {return iCartService.updateCartItem(updateCart);}
    @DeleteMapping("/{cartId}")
    ResponseEntity<ResponseObject> deleteCartItem(@PathVariable int cartId)
    {
        return iCartService.deleteCartItem(cartId);
    }
}
