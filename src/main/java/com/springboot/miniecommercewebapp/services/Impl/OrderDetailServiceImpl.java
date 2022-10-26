package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.OrderDetailRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.services.ICartService;
import com.springboot.miniecommercewebapp.services.IOrderDetailService;
import com.springboot.miniecommercewebapp.services.IProductService;
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
    public ResponseEntity<SuccessResponse> getOrderItemsByOrderId(int orderId) {
        List<OrderItemsEntity> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "List found", orderDetailList));
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found", ""));
    }

    // When add ; Delete to current product quantity
    @Override
    public ResponseEntity<SuccessResponse> addOrderItem(int orderId, CartsEntity cartItem) {
//        // Check cart, must exist to delete, to get i4
//        boolean foundCart = cartRepository.findById(cartItem.getCartId()).isPresent();
//        // Check Order, must exist to mapping
//        boolean foundOrder = orderRepository.findById(orderId).isPresent();
//        // Check product, must exist to update quantity
//        Optional<ProductsEntity> foundProduct = productRepository.findByProductIdAndQuantity(cartItem.getProductId(), cartItem.getQuantity());
//        if (foundProduct.isPresent()) {
//            if (foundOrder) {
//                if (foundCart) {
//                    OrderItemsEntity newOrderDetail = new OrderItemsEntity(null, cartItem.getQuantity(), cartItem.getPrice(), orderId, cartItem.getProductId(), null, null);
//                    orderDetailRepository.save(newOrderDetail);
//                    iCartService.deleteCartItem(cartItem.getCartId());
//                    iProductService.updateProduct(foundProduct.get().getProductId(), cartItem.getQuantity());
//                    return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "insert ok", newOrderDetail));
//                } else {
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Cart item not found", ""));
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Order not found", ""));
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Product not found", ""));
//        }
        return  null;
    }

    @Override
    public ResponseEntity<SuccessResponse> cancelOrderDetail(OrderItemsEntity cancelOrderDetail) {
//        Optional<ProductsEntity> foundProduct = productRepository.findById(cancelOrderDetail.getProductId());
//        if (foundProduct.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Product not found", iProductService.updateProduct(foundProduct.get(), foundProduct.get().getProductId(), -(cancelOrderDetail.getQuantity()))));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Product not found", ""));
//        }
        return  null;
    }
}
