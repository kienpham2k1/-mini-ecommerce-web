package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.OrderDetail;
import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.OrderDetailRepository;
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

    @Override
    public ResponseEntity<ResponseObject> getOrderItemsByOrderId(int orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "List found", orderDetailList));
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found", ""));
    }

    // When add; Delete to current product quantity
    @Override
    public ResponseEntity<ResponseObject> addOrderItem(OrderDetail newOrderItem) {
        //found OrderDetail, If not exist insert into
        Optional<OrderDetail> foundOrderItem = orderDetailRepository.findByOrderIdAndProductId(newOrderItem.getOrderId(), newOrderItem.getProductId());
        Optional<Product> foundProduct = productRepository.findById(newOrderItem.getProductId());
        if (foundOrderItem.isEmpty()) {
            //found product to get the quantity, if not found return failed.
            if (foundProduct.isPresent()) {
                if (newOrderItem.getQuantity() <= foundProduct.get().getQuantity()) {
                    //update product quantity
                    iProductService.updateProduct(foundProduct.get(), newOrderItem.getProductId(), (newOrderItem.getQuantity()));
                    //ok, add to order detail table
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Insert successfuly", orderDetailRepository.save(newOrderItem)));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Product do not have enough quantity", ""));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", "Product not found", ""));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", "Order item all ready exist", ""));
        }
    }
}
