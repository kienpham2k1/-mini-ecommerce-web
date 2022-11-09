package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.models.enums.EOrderStatus;
import com.springboot.miniecommercewebapp.repositories.OrderItemRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import com.springboot.miniecommercewebapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<OrdersEntity> getAllOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<OrdersEntity> orderList = orderRepository.findByUserIdOrderByOrderDateDesc(auth.getName());
        if (orderList.size() > 0) {
            return orderList;
        }
        throw new NotFoundException("Not found orders");
    }

    @Override
    public Optional<OrdersEntity> getOrder(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (foundOrder.isPresent()) {
            if (!auth.getName().equalsIgnoreCase(foundOrder.get().getUserId()))
                throw new AccessDeniedException("You dont have permistion to do this");
            return foundOrder;
        }
        throw new NotFoundException("Not found order");
    }

    private void isValidCart(List<CartsEntity> cartList) {
        for (CartsEntity cart : cartList) {
            Optional<ProductsEntity> checkProduct = productRepository.findById(cart.getProductId());
            if (checkProduct.isPresent()) {
                Optional<ProductsEntity> product = productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(checkProduct.get().getProductId(),
                        cart.getQuantity());
                if (product.isEmpty()) throw new NotFoundException("Product does not have enought quantity");
            } else
                throw new NotFoundException("Product does not exist");
        }
    }

    @Override
    public OrdersEntity addOrder(List<CartsEntity> listCart) {
        if (listCart.size() > 0) {
            isValidCart(listCart);
            double total = 0;
            for (CartsEntity cartItem : listCart) {
                total += cartItem.getPrice();
            }
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            OrdersEntity newOrder = new OrdersEntity();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            newOrder.setUserId(auth.getName());
            newOrder.setStatus(EOrderStatus.REQUEST);
            newOrder.setOrderDate(sqlDate);
            newOrder.setTotal(total);
            OrdersEntity insertOrder = orderRepository.save(newOrder);
            listCart.stream().forEach(cart -> {
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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getName().equalsIgnoreCase(foundOrder.get().getUserId()))
                throw new AccessDeniedException("You dont have permistion to do this");
            foundOrder.get().setStatus(EOrderStatus.valueOf(updateStatus));
            return orderRepository.save(foundOrder.get());
        }
        throw new NotFoundException("Not found Order");
    }

    @Override
    // return quantity to product
    public boolean cancelOrder(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getName().equalsIgnoreCase(foundOrder.get().getUserId()))
                throw new AccessDeniedException("You dont have permistion to do this");
            if (foundOrder.get().getStatus().equals(EOrderStatus.COMPLETED)
                    || foundOrder.get().getStatus().equals(EOrderStatus.DENIED)
                    || foundOrder.get().getStatus().equals(EOrderStatus.CANCELLED)) {
                throw new IllegalArgumentException("Bad request");
            }
            foundOrder.get().setStatus(EOrderStatus.CANCELLED);
            orderRepository.save(foundOrder.get());
            List<OrderItemsEntity> foundOrderItems = orderItemRepository.findByOrderId(orderId);
            if (foundOrderItems.size() > 0) {
                foundOrderItems.stream().forEach(orderDetail -> {
                    iOrderDetailService.cancelOrderDetail(orderDetail.getDetailId());
                });
                return true;
            }
        }
        throw new NotFoundException("Order not found");
    }
}
