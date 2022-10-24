package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.CartEntity;
import com.springboot.miniecommercewebapp.models.OrderDetailEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.services.orderDetailService.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    IOrderDetailService iOrderDetailService;

    @GetMapping("{orderId}")
    ResponseEntity<ResponseObject> getAllOrderItemsByOrderId(@PathVariable int orderId) {
        return iOrderDetailService.getOrderItemsByOrderId(orderId);
    }

    @PostMapping("{orderId}")
    ResponseEntity<ResponseObject> addNewOrderDetail(@PathVariable int orderId, @RequestBody CartEntity cartItem) {
        return iOrderDetailService.addOrderItem(orderId, cartItem);
    }
    @PutMapping()
    ResponseEntity<ResponseObject> cancelOrderDetail(@RequestBody OrderDetailEntity candelOderItem) {
        return iOrderDetailService.cancelOrderDetail(candelOderItem);
    }
}
