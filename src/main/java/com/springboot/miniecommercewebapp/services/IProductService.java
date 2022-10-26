package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.ProductsEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface
IProductService {
    List<ProductsEntity> getAllProducts();

    Page<ProductsEntity> getProductsWithPage(int page, int size, String sortable, String sort);

    List<ProductsEntity> getProductsByCategoryId(int categoryId);

    Optional<ProductsEntity> getProductById(int productId);
    ProductsEntity addNewProduct(ProductsEntity newProduct);

    // Update full
    ProductsEntity updateProduct( int id,ProductsEntity newProduct, int quantity);

    // Update quantity
    Optional<ProductsEntity> updateProduct(int id, int quantity);

    // Update status
    boolean updateStatusProduct(int id, int newStatus);
}
