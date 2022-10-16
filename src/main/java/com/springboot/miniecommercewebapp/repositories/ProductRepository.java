package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCatagoryId(int categoryId);
    List<Product> findByProductName(String productName);

}
