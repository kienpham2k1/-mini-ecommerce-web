package com.springboot.miniecommercewebapp.services.rateService;

import com.springboot.miniecommercewebapp.models.RateEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IRateService {
    ResponseEntity<ResponseObject> getALlRatingByProductId(int productId);
    ResponseEntity<ResponseObject> getRatingDetail(int productId, String userId);
    ResponseEntity<ResponseObject> addNewRate(RateEntity newRating);
    ResponseEntity<ResponseObject> updateRate(RateEntity updateRatting);
    ResponseEntity<ResponseObject> deleteRating(int ratingId);
}
