package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductsEntity, Integer> {
    Page<ProductsEntity> findByCatagoryId(int categoryId, Pageable pageable);
    Page<ProductsEntity> findAllByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    Page<ProductsEntity> findAllByProductNameContainingIgnoreCaseAndCatagoryId(String productName, int categoryId, Pageable pageable);

    Optional<ProductsEntity> findByProductName(String productName);

    @Query("select p from ProductsEntity p where p.productId = ?1 and p.quantity >= ?2")
    Optional<ProductsEntity> findByProductIdAndQuantity(int productId, int quantityCheck);
}
