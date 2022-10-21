package com.springboot.miniecommercewebapp.services.orderService;

import com.springboot.miniecommercewebapp.dto.CartSelected;
import com.springboot.miniecommercewebapp.models.Cart;
import com.springboot.miniecommercewebapp.models.Order;
import com.springboot.miniecommercewebapp.models.OrderDetail;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.OrderDetailRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.services.orderDetailService.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    IOrderDetailService iOrderDetailService;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllOrders(String userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        if (orderList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found", orderList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getOrder(int orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found", foundOrder));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> addOrder(CartSelected newOrder) {
        if (newOrder.getCartList().size() > 0) {
            double total = 0;
            for (Cart cartItem : newOrder.getCartList()) {
                total += cartItem.getPrice();
            }
            Order insertOrder = orderRepository.save(newOrder.getNewOrder());
            newOrder.getCartList().stream().forEach(cart -> {
                iOrderDetailService.addOrderItem(insertOrder.getOrderId(), cart);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "found", insertOrder));
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("Failed", "list Cart null", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> updateOrder(int orderId, int updateStatus) {
        Optional<Order> foundOrder = orderRepository.findById(orderId).map(order -> {
            order.setStatus(false);
            return orderRepository.save(order);
        });
        if (foundOrder.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update success", ""));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Order", ""));
    }

    @Override
    // return quantity to product
    public ResponseEntity<ResponseObject> cancelOrder(int orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId).map(order -> {
            order.setStatus(false);
            return orderRepository.save(order);
        });
        List<OrderDetail> foundOrderDetail = orderDetailRepository.findByOrderId(orderId);
        if (foundOrder.isPresent()) {
            if (foundOrderDetail.size() > 0) {
                foundOrderDetail.stream().forEach(orderDetail -> {
                    iOrderDetailService.cancelOrderDetail(orderDetail);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "cancel success", ""));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Order", ""));
    }
}
