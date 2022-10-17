package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p")
    Page<Product> findProducts(Pageable pageable);
    List<Product> findByCatagoryId(int categoryId);
    List<Product> findByProductName(String productName);
}
