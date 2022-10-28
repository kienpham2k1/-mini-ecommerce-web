package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.RatingsEntity;
import com.springboot.miniecommercewebapp.services.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rate")
public class RateController {
    @Autowired
    IRateService iRatingService;

    @GetMapping("{productId}")
    ResponseEntity<?> getAllRateOfProduct(@PathVariable int productId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found success", iRatingService.getALlRatingByProductId(productId)),
                HttpStatus.OK);
    }

    @GetMapping("/{productId}/user")
    ResponseEntity<?> getAllRateOfProduct(@PathVariable int productId, @RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Found success", iRatingService.getRatingDetail(productId, userId)),
                HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<?> addNewRating(@Valid @RequestBody RatingsEntity rate) {
        return new ResponseEntity<>(new SuccessResponse(200, "Add new success", iRatingService.addNewRate(rate)), HttpStatus.OK);
    }

    @PutMapping("")
    ResponseEntity<?> updateRating(@RequestParam(name = "rateId") int rateId, @Valid @RequestBody RatingsEntity rate) {
        return new ResponseEntity<>(new SuccessResponse(200, "Update success", iRatingService.updateRate(rateId, rate))
                , HttpStatus.OK);
    }


    @DeleteMapping("{ratingId}")
    ResponseEntity<?> deleteRating(@PathVariable int ratingId) {
        return new ResponseEntity<>(new SuccessResponse(200, "Delete success", iRatingService.deleteRating(ratingId))
                , HttpStatus.OK);
    }
}
