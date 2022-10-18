package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Rating;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements IRatingService {
    @Autowired
    RatingRepository ratingRepository;

    @Override
    public ResponseEntity<ResponseObject> getALlRatingByProductId(int productId) {
        List<Rating> ratingList = ratingRepository.findByProductId(productId);
        if (ratingList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "OK", ratingList));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Rating", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> getRatingDetail(int productId, String userId) {
        Optional<Rating> foundRatingDetail = ratingRepository.findByProductIdAndUserId(productId, userId);
        if (foundRatingDetail.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "OK", foundRatingDetail));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Rating detail", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> addNewRate(Rating newRating) {
        Optional<Rating> foundRatingDetail = ratingRepository.findByProductIdAndUserId(newRating.getProductId(), newRating.getUserId());
        if (foundRatingDetail.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "OK", ratingRepository.save(newRating)));
        else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", "Rating has exist", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> updateRate(Rating updateRatting) {
        Optional<Rating> foundRatingDetail = ratingRepository.findByProductIdAndUserId(updateRatting.getProductId(), updateRatting.getUserId())
                .map(rating -> {
                    rating.setScore(updateRatting.getScore());
                    return ratingRepository.save(rating);
                });
        if (foundRatingDetail.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "OK", foundRatingDetail));
        else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", "Rating not found", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteRating(int ratingId) {
        boolean isExist = ratingRepository.existsById(ratingId);
        if (isExist) {
            ratingRepository.deleteById(ratingId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete succesfully", "")
            );
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("ok", "NOT Found rating to delete", "")
        );
    }
}
