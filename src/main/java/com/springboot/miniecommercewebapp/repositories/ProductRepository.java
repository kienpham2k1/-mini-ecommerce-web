package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByCatagoryId(int categoryId);

    List<ProductEntity> findByProductName(String productName);

    @Query("select p from ProductEntity p where p.productId = ?1 and p.quantity >= ?2")
    Optional<ProductEntity> findByProductIdAndQuantity(int productId, int quantityCheck);
}
