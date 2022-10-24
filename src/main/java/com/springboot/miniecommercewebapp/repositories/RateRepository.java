package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Integer> {
    List<RateEntity> findByProductId(int productId);
    Optional<RateEntity> findByProductIdAndUserId(int productId, String userId);
}
