package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductsEntity, Integer> {
    Page<ProductsEntity> findByCatagoryId(int categoryId, Pageable pageable);

    Page<ProductsEntity> findAllByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    Page<ProductsEntity> findAllByProductNameContainingIgnoreCaseAndCatagoryId(String productName, int categoryId, Pageable pageable);

    Optional<ProductsEntity> findByProductName(String productName);

    @Query("select p from ProductsEntity p where p.productId = ?1 and p.quantity >= ?2 and upper(p.status) = upper" + "('STOCKING')")
    Optional<ProductsEntity> findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(int productId, int quantityCheck);


    @Query("select p from ProductsEntity p where p.quantity > 0 and upper(p.status) = upper('STOCKING')")
    Page<ProductsEntity> findAllByQuantityGreaterThanAndStatusEqualsIgnoreCase(Pageable pageable);

    @Query("select p from ProductsEntity p where p.catagoryId = ?1 and p.quantity > 0 " + "and upper(p.status) = upper('STOCKING')")
    Page<ProductsEntity> findByCatagoryIdAndQuantityGreaterThanAndStatusEqualsIgnoreCase(int categoryId, Pageable pageable);

    @Query("select p from ProductsEntity p " + "where upper(p.productName) like upper(concat('%', ?1, '%')) and p.quantity > 0 and upper(p.status) = " + "upper('STOKCING')")
    Page<ProductsEntity> findAllByProductNameContainingIgnoreCaseAndQuantityGreaterThanAndStatusEqualsIgnoreCase(String productName, Pageable pageable);

    @Query("select p from ProductsEntity p " + "where upper(p.productName) like upper(concat('%', ?1, '%')) and p.catagoryId = ?2 and p.quantity > 0 and" + " upper(p.status) = upper('STOCKING')")
    Page<ProductsEntity> findAllByProductNameContainingIgnoreCaseAndCatagoryIdAndQuantityGreaterThanAndStatusEqualsIgnoreCase(String productName, int categoryId, Pageable pageable);
}
