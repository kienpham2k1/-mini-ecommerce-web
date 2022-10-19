package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.OrderDetail;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.services.IOrderDetailService;
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
    ResponseEntity<ResponseObject> addNewOrderDetail(@PathVariable int orderId, @RequestBody Cart cartItem) {
        return iOrderDetailService.addOrderItem(orderId, cartItem);
    }
    @PutMapping()
    ResponseEntity<ResponseObject> cancelOrderDetail(@RequestBody OrderDetail candelOderItem) {
        return iOrderDetailService.cancelOrderDetail(candelOderItem);
    }
}
