package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    IOrderItemService iOrderDetailService;

    @GetMapping("{orderId}")
    public ResponseEntity<?> getAllOrderItemsByOrderId(@PathVariable int orderId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found success",
                iOrderDetailService.getOrderItemsByOrderId(orderId)), HttpStatus.OK);
    }
}
