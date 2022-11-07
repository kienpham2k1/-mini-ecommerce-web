package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.request.CartSelected;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    IOrderService iOrderService;

    @GetMapping("")
    ResponseEntity<?> getOrders(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found Success", iOrderService.getAllOrders(userId)), HttpStatus.OK);
    }

    @GetMapping("{orderId}")
    ResponseEntity<?> getOrders(@PathVariable int orderId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found Success", iOrderService.getOrder(orderId)), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<?> addOrder(@RequestBody CartSelected newCartSelected) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found Success", iOrderService.addOrder(newCartSelected)), HttpStatus.CREATED);
    }

    @PutMapping("{orderId}")
    ResponseEntity<?> updateOrder(@PathVariable int orderId, @RequestParam(name = "status") String status) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found Success", iOrderService.updateOrder(orderId, status)), HttpStatus.OK);
    }

    @DeleteMapping("{orderId}")
    ResponseEntity<?> candelOrder(@PathVariable int orderId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found Success", iOrderService.cancelOrder(orderId)), HttpStatus.OK);
    }
}
