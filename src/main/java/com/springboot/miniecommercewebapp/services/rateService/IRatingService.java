package com.springboot.miniecommercewebapp.services.rateService;

import com.springboot.miniecommercewebapp.models.Rating;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IRatingService {
    ResponseEntity<ResponseObject> getALlRatingByProductId(int productId);
    ResponseEntity<ResponseObject> getRatingDetail(int productId, String userId);
    ResponseEntity<ResponseObject> addNewRate(Rating newRating);
    ResponseEntity<ResponseObject> updateRate(Rating updateRatting);
    ResponseEntity<ResponseObject> deleteRating(int ratingId);
}
