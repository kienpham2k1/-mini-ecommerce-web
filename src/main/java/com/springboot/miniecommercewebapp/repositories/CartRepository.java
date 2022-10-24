package com.springboot.miniecommercewebapp.repositories;


import com.springboot.miniecommercewebapp.models.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findByUserId(String userId);
    Optional<CartEntity> findByUserIdAndProductId(String userId, int productId);
}
