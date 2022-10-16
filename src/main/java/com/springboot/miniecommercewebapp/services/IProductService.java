package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> getAllProducts();
    ResponseEntity<ResponseObject> getProductsByCategoryId(int categoryId);
    ResponseEntity<ResponseObject> getProductDetailById(int id);
    ResponseEntity<ResponseObject> addNewProduct(Product newProduct);
    ResponseEntity<ResponseObject> updateProduct(Product newProduct, int id);
    ResponseEntity<ResponseObject> deleteProduct(int id);
}
