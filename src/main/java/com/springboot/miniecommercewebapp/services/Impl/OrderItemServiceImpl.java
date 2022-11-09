package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.OrderItemRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.ICartService;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import com.springboot.miniecommercewebapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    IProductService iProductService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ICartService iCartService;
    @Autowired
    CartRepository cartRepository;

    @Override
    public List<OrderItemsEntity> getOrderItemsByOrderId(int orderId) {
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getName().equalsIgnoreCase(foundOrder.get().getUserId())) {
                throw new AccessDeniedException("You dont have permistion to do this");
            } else {
                List<OrderItemsEntity> orderDetailList = orderItemRepository.findByOrderId(orderId);
                if (orderDetailList.size() > 0) {
                    return orderDetailList;
                }
                throw new NotFoundException("Order item list not found");
            }
        }
        throw new NotFoundException("Order not found");
    }

    // When add ; Delete to current product quantity
    @Override
    public OrderItemsEntity addOrderItem(int orderId, CartsEntity cartItem) {
        // Check cart, must exist to delete, to get i4
        boolean foundCart = cartRepository.findById(cartItem.getCartId()).isPresent();
        // Check Order, must exist to mapping
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        // Check product, must exist to update quantity
        Optional<ProductsEntity> foundProduct = productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(cartItem.getProductId(), cartItem.getQuantity());
        if (foundProduct.isPresent()) {
            if (foundOrder.isPresent()) {
                if (foundCart) {
                    OrderItemsEntity newOrderDetail = new OrderItemsEntity(cartItem.getQuantity(), cartItem.getPrice(),
                            orderId,
                            cartItem.getProductId());
                    orderItemRepository.save(newOrderDetail);
                    iCartService.deleteCartItem(cartItem.getCartId());
                    iProductService.updateProduct(foundProduct.get().getProductId(), cartItem.getQuantity());
                    return newOrderDetail;
                }
                throw new NotFoundException("Cart item not found");
            }
            throw new NotFoundException("Order not found");
        }
        throw new NotFoundException("Product not found");
    }

    @Override
    public boolean cancelOrderDetail(int orderItemId) {
        Optional<OrderItemsEntity> foundOrderItem = orderItemRepository.findById(orderItemId);
        if (foundOrderItem.isPresent()) {
            Optional<ProductsEntity> foundProduct = productRepository.findById(foundOrderItem.get().getProductId());
            if (foundProduct.isPresent()) {
               ProductsEntity e =  iProductService.updateProduct(foundProduct.get().getProductId(),
                        -(foundOrderItem.get().getQuantity()));
                return true;
            }
            throw new NotFoundException("Not found product");
        }
        throw new NotFoundException("Not found order item");
    }
}
