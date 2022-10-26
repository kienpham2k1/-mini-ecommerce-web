package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.dto.request.CartSelected;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.OrderDetailRepository;
import com.springboot.miniecommercewebapp.services.IOrderService;
import com.springboot.miniecommercewebapp.services.IOrderDetailService;
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
    public ResponseEntity<SuccessResponse> getAllOrders(String userId) {
        List<OrdersEntity> orderList = orderRepository.findByUserId(userId);
        if (orderList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "found", orderList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found", ""));
        }
    }

    @Override
    public ResponseEntity<SuccessResponse> getOrder(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "found", foundOrder));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found", ""));
        }
    }

    @Override
    public ResponseEntity<SuccessResponse> addOrder(CartSelected newOrder) {
//        if (newOrder.getCartList().size() > 0) {
//            double total = 0;
//            for (CartsEntity cartItem : newOrder.getCartList()) {
//                total += cartItem.getPrice();
//            }
//            OrdersEntity insertOrder = orderRepository.save(newOrder.getNewOrder());
//            newOrder.getCartList().stream().forEach(cart -> {
//                iOrderDetailService.addOrderItem(insertOrder.getOrderId(), cart);
//            });
//            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "found", insertOrder));
//        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
//                new SuccessResponse("Failed", "list Cart null", ""));
    return null;
    }

    @Override
    public ResponseEntity<SuccessResponse> updateOrder(int orderId, int updateStatus) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId).map(order -> {
            order.setStatus(1);
            return orderRepository.save(order);
        });
        if (foundOrder.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Update success", ""));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found Order", ""));
    }

    @Override
    // return quantity to product
    public ResponseEntity<SuccessResponse> cancelOrder(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId).map(order -> {
            order.setStatus(1);
            return orderRepository.save(order);
        });
        List<OrderItemsEntity> foundOrderDetail = orderDetailRepository.findByOrderId(orderId);
        if (foundOrder.isPresent()) {
            if (foundOrderDetail.size() > 0) {
                foundOrderDetail.stream().forEach(orderDetail -> {
                    iOrderDetailService.cancelOrderDetail(orderDetail);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "cancel success", ""));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found Order", ""));
    }
}
