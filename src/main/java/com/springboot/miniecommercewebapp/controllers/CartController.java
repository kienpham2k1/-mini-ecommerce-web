package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cart")
public class CartController {
    @Autowired
    ICartService iCartService;

    @GetMapping("")
    ResponseEntity<?> getAllCartItem(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(new SuccessResponse("200", "Found success ", iCartService.getCartItemsByUserId(userId)), HttpStatus.OK);
        }

    @PostMapping("")
    ResponseEntity<?> addNewCart(@RequestBody @Valid CartsEntity newCart) {
        return
                new ResponseEntity<>(new SuccessResponse("200", "Add success "
                , iCartService.addToCart(newCart)
        ), HttpStatus.OK);
    }

    @PutMapping("{cartId}")
    ResponseEntity<?> updateCart(@PathVariable int cartId, @RequestBody @Valid CartsEntity updateCart) {
        return
        new ResponseEntity<>(new SuccessResponse("200", "Add success "
                , iCartService.updateCartItem(cartId, updateCart, 0)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    ResponseEntity<?> deleteCartItem(@PathVariable int cartId) {
        return
        new ResponseEntity<>(new SuccessResponse("200", "Add success "
                , iCartService.deleteCartItem(cartId)
        ), HttpStatus.OK);
    }
}
