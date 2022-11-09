package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.RatingsEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IRateService {
    Optional<RatingsEntity> getRatingDetail(int productId, int orderId);
    RatingsEntity addNewRate(RatingsEntity newRating,int orderId);
    RatingsEntity updateRate(int rateId, RatingsEntity updateRatting);
    boolean deleteRating(int ratingId);
}
