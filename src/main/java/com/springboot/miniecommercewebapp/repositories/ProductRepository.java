package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductsEntity, Integer> {

    List<ProductsEntity> findByCatagoryId(int categoryId);

    Optional<ProductsEntity> findByProductName(String productName);

    @Query("select p from ProductsEntity p where p.productId = ?1 and p.quantity >= ?2")
    Optional<ProductsEntity> findByProductIdAndQuantity(int productId, int quantityCheck);
}
