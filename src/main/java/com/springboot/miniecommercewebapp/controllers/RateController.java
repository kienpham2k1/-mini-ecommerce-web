package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.RateEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.services.rateService.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate")
public class RateController {
    @Autowired
    IRateService iRatingService;

    @GetMapping("{productId}")
    ResponseEntity<ResponseObject> getAllRateOfProduct(@PathVariable int productId) {
        return iRatingService.getALlRatingByProductId(productId);
    }

    @GetMapping("/detail/{productId}")
    ResponseEntity<ResponseObject> getAllRateOfProduct(@PathVariable int productId, @RequestParam(name = "userId") String userId) {
        return iRatingService.getRatingDetail(productId, userId);
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> addNewRating(@RequestBody RateEntity rate) {
        return iRatingService.addNewRate(rate);
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> updateRating(@RequestBody RateEntity rate) {
        return iRatingService.updateRate(rate);
    }

    @DeleteMapping("{ratingId}")
    ResponseEntity<ResponseObject> deleteRating(@PathVariable int ratingId) {
        return iRatingService.deleteRating(ratingId);
    }
}
