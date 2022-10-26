package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RatingsEntity, Integer> {
    List<RatingsEntity> findByProductId(int productId);
    Optional<RatingsEntity> findByProductIdAndUserId(int productId, String userId);
}
