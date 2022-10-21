package com.springboot.miniecommercewebapp.services.orderDetailService;

import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.models.*;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.OrderDetailRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.productServices.IProductService;
import com.springboot.miniecommercewebapp.services.cartService.ICartService;
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
        boolean foundCart = cartRepository.findById(cartItem.getCartId()).isPresent();
        // Check Order, must exist to mapping
        boolean foundOrder = orderRepository.findById(orderId).isPresent();
        // Check product, must exist to update quantity
        Optional<Product> foundProduct = productRepository.findByProductIdAndQuantity(cartItem.getProductId(), cartItem.getQuantity());
        if (foundProduct.isPresent()) {
            if (foundOrder) {
                if (foundCart) {
                    OrderDetail newOrderDetail = new OrderDetail(null, cartItem.getQuantity(), cartItem.getPrice(), orderId, cartItem.getProductId(), null, null);
                    orderDetailRepository.save(newOrderDetail);
                    iCartService.deleteCartItem(cartItem.getCartId());
                    iProductService.updateProduct(foundProduct.get().getProductId(), cartItem.getQuantity());
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "insert ok", newOrderDetail));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cart item not found", ""));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Order not found", ""));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Product not found", ""));
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
