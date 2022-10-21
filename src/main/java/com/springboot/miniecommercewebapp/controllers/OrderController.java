package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.CartSelected;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.services.orderService.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    IOrderService iOrderService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getOrders(@RequestParam(name = "userId") String userId) {
        return iOrderService.getAllOrders(userId);
    }

    @GetMapping("{orderId}")
    ResponseEntity<ResponseObject> getOrders(@PathVariable int orderId) {
        return iOrderService.getOrder(orderId);
    }

    @PostMapping()
    ResponseEntity<ResponseObject> addOrder(@RequestBody CartSelected newCartSelected) {
//        Order ord = new Order(1, Date.valueOf(java.time.LocalDate.now()), 1.2, "kienpt2k1", true, null, null);
//        Cart newCart = new Cart(1,"kienpt2k1", 1, 1, 3.3, null, null);
//        List<Cart> cartList = new ArrayList<>();
//        cartList.add(newCart);
//        cartList.add(newCart);
//        cartList.add(newCart);
//        CartSelected cs = new CartSelected();
//        cs.setNewOrder(ord);
//        cs.setCartList(cartList);
//        if (newCartSelected == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Null", "Null", cartList));
//        }
//        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Null", "Null", newCartSelected));
        return iOrderService.addOrder(newCartSelected);
    }

    @PutMapping("{orderId}")
    ResponseEntity<ResponseObject> updateOrder(@PathVariable int orderId, @RequestParam(name = "status") int status) {
        return iOrderService.updateOrder(orderId, status);
    }

    @DeleteMapping("{orderId}")
    ResponseEntity<ResponseObject> candelOrder(@PathVariable int orderId) {
        return iOrderService.cancelOrder(orderId);
    }
}
