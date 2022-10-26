package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.RatingsEntity;
import com.springboot.miniecommercewebapp.repositories.RateRepository;
import com.springboot.miniecommercewebapp.services.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    RateRepository ratingRepository;

    @Override
    public List<RatingsEntity> getALlRatingByProductId(int productId) {
        List<RatingsEntity> rateList = ratingRepository.findByProductId(productId);
        if (rateList.size() > 0) return rateList;
        throw new NotFoundException("Not found rate of product");
    }

    @Override
    public Optional<RatingsEntity> getRatingDetail(int productId, String userId) {
        Optional<RatingsEntity> foundRatingDetail = ratingRepository.findByProductIdAndUserId(productId, userId);
        if (foundRatingDetail.isPresent()) return foundRatingDetail;
        throw new NotFoundException("Not found rate detail of product");
    }

    @Override
    public RatingsEntity addNewRate(RatingsEntity newRating) {
        Optional<RatingsEntity> foundRatingDetail = ratingRepository.findByProductIdAndUserId(newRating.getProductId(), newRating.getUserId());
        if (foundRatingDetail.isEmpty()) return ratingRepository.save(newRating);
        throw new ItemExistException("Already exist");
    }

    @Override
    public Optional<RatingsEntity> updateRate(RatingsEntity updateRatting) {
        Optional<RatingsEntity> foundRatingDetail = ratingRepository.findByProductIdAndUserId(updateRatting.getProductId(), updateRatting.getUserId()).map(rating -> {
            rating.setScore(updateRatting.getScore());
            return ratingRepository.save(rating);
        });
        if (foundRatingDetail.isPresent()) return foundRatingDetail;
        throw new NotFoundException("Not fount to update");
    }

    @Override
    public boolean deleteRating(int ratingId) {
        boolean isExist = ratingRepository.existsById(ratingId);
        if (isExist) {
            ratingRepository.deleteById(ratingId);
            return true;
        }
        throw new NotFoundException("Not found to delete");
    }
}