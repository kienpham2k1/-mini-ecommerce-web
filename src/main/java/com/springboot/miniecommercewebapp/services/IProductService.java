package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import org.springframework.http.ResponseEntity;


public interface IProductService {
    ResponseEntity<ResponseObject> getAllProducts();

    ResponseEntity<ResponseObject> getProductsWithPage(int page, int size, String sortable, String sort);

    ResponseEntity<ResponseObject> getProductsByCategoryId(int categoryId);

    ResponseEntity<ResponseObject> getProductDetailById(int id);

    ResponseEntity<ResponseObject> addNewProduct(Product newProduct);

    ResponseEntity<ResponseObject> updateProduct(Product newProduct, int id);

    ResponseEntity<ResponseObject> deleteProduct(int id);
}
