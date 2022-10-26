package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.RatingsEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IRateService {
    List<RatingsEntity> getALlRatingByProductId(int productId);
    Optional<RatingsEntity> getRatingDetail(int productId, String userId);
    RatingsEntity addNewRate(RatingsEntity newRating);
    Optional<RatingsEntity> updateRate(RatingsEntity updateRatting);
    boolean deleteRating(int ratingId);
}
