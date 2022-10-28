package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    IOrderItemService iOrderDetailService;

    @GetMapping("{orderId}")
    ResponseEntity<?> getAllOrderItemsByOrderId(@PathVariable int orderId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found success",
                iOrderDetailService.getOrderItemsByOrderId(orderId)
        ), HttpStatus.CREATED);
    }

    @PostMapping("{orderId}")
    ResponseEntity<?> addNewOrderDetail(@PathVariable int orderId, @RequestBody CartsEntity cartItem) {
       // return iOrderDetailService.addOrderItem(orderId, cartItem);
        return  new ResponseEntity<>(new SuccessResponse(200, "Add success", iOrderDetailService.addOrderItem(orderId, cartItem)),
                HttpStatus.OK);
    }

    @PutMapping("")
    ResponseEntity<?> cancelOrderDetail(@RequestParam(name = "candelOderItemId") int candelOderItemId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Cancel success", iOrderDetailService.cancelOrderDetail(candelOderItemId)),
                HttpStatus.OK);
    }
}
