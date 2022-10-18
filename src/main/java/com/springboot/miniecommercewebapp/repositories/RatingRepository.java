package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByProductId(int productId);
    Optional<Rating> findByProductIdAndUserId(int productId, String userId);
}
