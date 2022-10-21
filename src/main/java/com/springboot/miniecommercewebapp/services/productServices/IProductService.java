package com.springboot.miniecommercewebapp.services.productServices;

import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;


public interface IProductService {
    ResponseEntity<ResponseObject> getAllProducts();

    ResponseEntity<ResponseObject> getProductsWithPage(int page, int size, String sortable, String sort);

    ResponseEntity<ResponseObject> getProductsByCategoryId(int categoryId);

    ResponseEntity<ResponseObject> getProductDetailById(int id);

    ResponseEntity<ResponseObject> addNewProduct(Product newProduct);

    // Update full
    ResponseEntity<ResponseObject> updateProduct(Product newProduct, int id, int quantity);

    // Update quantity
    ResponseEntity<ResponseObject> updateProduct(int id, int quantity);

    // Update status
    ResponseEntity<ResponseObject> updateStatusProduct(int id, int newStatus);
}
