package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCatagoryId(int categoryId);

    List<Product> findByProductName(String productName);

    @Query("select p.quantity from Product p where p.productId = ?1 and p.quantity >= ?2")
    Optional<Integer> findByProductIdAndQuantity(int productId, int quantityCheck);
}
