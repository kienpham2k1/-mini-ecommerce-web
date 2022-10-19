package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.*;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.OrderDetailRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
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
    public ResponseEntity<ResponseObject> getOrderItemsByOrderId(int orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "List found", orderDetailList));
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found", ""));
    }

    // When add ; Delete to current product quantity
    @Override
    public ResponseEntity<ResponseObject> addOrderItem(int orderId, Cart cartItem) {
        // Check cart, must exist to delete, to get i4
        Optional<Cart> foundCart = cartRepository.findById(cartItem.getCartId());
        // Check Order, must exist to mapping
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        // Check product, must exist to delete quantity
        Optional<Product> foundProduct = productRepository.findByProductIdAndQuantity(cartItem.getProductId(), cartItem.getQuantity());
        if (foundOrder.isPresent()) {
            if (foundProduct.isPresent()) {
                if (foundCart.isPresent()) {
                    OrderDetail newOrderDetail = new OrderDetail(null, cartItem.getQuantity(), cartItem.getPrice(), orderId, cartItem.getProductId(), null, null);
                    iProductService.updateProduct(foundProduct.get(), cartItem.getProductId(), cartItem.getQuantity());
                    iCartService.deleteCartItem(cartItem.getCartId());
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "insert ok", newOrderDetail));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cart item not found", ""));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Product not found", ""));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Order not found", ""));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> cancelOrderDetail(OrderDetail cancelOrderDetail) {
        Optional<Product> foundProduct = productRepository.findById(cancelOrderDetail.getProductId());
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Product not found", iProductService.updateProduct(foundProduct.get(), foundProduct.get().getProductId(), -(cancelOrderDetail.getQuantity()))));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Product not found", ""));
        }
    }
}
