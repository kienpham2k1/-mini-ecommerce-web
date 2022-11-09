package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import com.springboot.miniecommercewebapp.models.RatingsEntity;
import com.springboot.miniecommercewebapp.models.enums.EOrderStatus;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.RateRepository;
import com.springboot.miniecommercewebapp.services.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    RateRepository ratingRepository;
    @Autowired
    OrderRepository orderRepository;

//    @Override
//    public List<RatingsEntity> getALlRatingByProductId(int productId) {
//        List<RatingsEntity> rateList = ratingRepository.findByProductId(productId);
//        if (rateList.size() > 0) return rateList;
//        throw new NotFoundException("Not found rate of product");
//    }

    @Override
    public Optional<RatingsEntity> getRatingDetail(int productId, int orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent() && foundOrder.get().getStatus().equals(EOrderStatus.COMPLETED)) {
            Optional<RatingsEntity> foundRatingDetail = ratingRepository.findByProductIdAndUserId(productId, auth.getName());
            if (foundRatingDetail.isPresent()) return foundRatingDetail;
            throw new NotFoundException("Not found rate detail of product");
        }
        throw new IllegalArgumentException("You must order this product and order had success");
    }

    @Override
    public RatingsEntity addNewRate(RatingsEntity newRating, int orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<OrdersEntity> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isPresent() && foundOrder.get().getStatus().equals(EOrderStatus.COMPLETED)) {
            Optional<RatingsEntity> foundRatingDetail =
                    ratingRepository.findByProductIdAndUserId(newRating.getProductId(), auth.getName());
            if (foundRatingDetail.isEmpty()) return ratingRepository.save(newRating);
            throw new ItemExistException("Already exist");
        }
        throw new IllegalArgumentException("You must order this product and order had success");
    }

    @Override
    public RatingsEntity updateRate(int rateId, RatingsEntity updateRatting) {
        Optional<RatingsEntity> foundRatingDetail = ratingRepository.findById(rateId);
        if (foundRatingDetail.isPresent()) {
            updateRatting.setRatingId(rateId);
            return ratingRepository.save(updateRatting);
        }
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