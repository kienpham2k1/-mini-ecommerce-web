package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.dto.request.CartSelected;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import com.springboot.miniecommercewebapp.models.enums.EOrderStatus;
import com.springboot.miniecommercewebapp.repositories.OrderItemRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import com.springboot.miniecommercewebapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    IOrderItemService iOrderDetailService;
    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<OrdersEntity> getAllOrders(String userId) {
        List<OrdersEntity> orderList = orderRepository.findByUserId(userId);
        if (orderList.size() > 0) {
            return orderList;
        }
        throw new NotFoundException("Not found orders");
    }

    @Override
    public Optional<OrdersEntity> getOrder(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent()) {
            return foundOrder;
        }
        throw new NotFoundException("Not found order");
    }

    @Override
    public OrdersEntity addOrder(CartSelected newOrder) {
        if (newOrder.getCartList().size() > 0) {
            double total = 0;
            for (CartsEntity cartItem : newOrder.getCartList()) {
                total += cartItem.getPrice();
            }
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            newOrder.getNewOrder().setStatus(EOrderStatus.REQUEST);
            newOrder.getNewOrder().setOrderDate(sqlDate);
            newOrder.getNewOrder().setTotal(total);
            OrdersEntity insertOrder = orderRepository.save(newOrder.getNewOrder());
            newOrder.getCartList().stream().forEach(cart -> {
                iOrderDetailService.addOrderItem(insertOrder.getOrderId(), cart);
            });
            return insertOrder;
        }
        throw new NotFoundException("Cart select null");
    }

    @Override
    public OrdersEntity updateOrder(int orderId, String updateStatus) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent()) {
            foundOrder.get().setStatus(EOrderStatus.valueOf(updateStatus));
            return orderRepository.save(foundOrder.get());
        }
        throw new NotFoundException("Not found Order");
    }

    @Override
    // return quantity to product
    public boolean cancelOrder(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId).map(order -> {
            order.setStatus(EOrderStatus.CANCELLED);
            return orderRepository.save(order);
        });
        List<OrderItemsEntity> foundOrderItems = orderItemRepository.findByOrderId(orderId);
        if (foundOrder.isPresent()) {
            if (foundOrderItems.size() > 0) {
                foundOrderItems.stream().forEach(orderDetail -> {
                    iOrderDetailService.cancelOrderDetail(orderDetail.getDetailId());
                });
            }
            return true;
        }
        throw new NotFoundException("Not found");
    }
}
