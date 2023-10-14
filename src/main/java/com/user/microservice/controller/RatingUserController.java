package com.user.microservice.controller;

import com.user.microservice.dto.RatingDto;
import com.user.microservice.payload.ApiResponse;
import com.user.microservice.service.RatingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/microservices/user")
public class RatingUserController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/rating")
    public ResponseEntity<ApiResponse> createRating(@RequestBody RatingDto ratingDto){
        RatingDto rating = this.ratingService.createRating(ratingDto);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("rating created").data(rating).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.CREATED);
    }

    @GetMapping("/rating")
//    @CircuitBreaker(name = "ratingServiceBreaker", fallbackMethod = "getAllRatingFail")
    @RateLimiter(name = "userRatingLimiter", fallbackMethod = "getAllRatingFail")
    public ResponseEntity<ApiResponse> getAllRating(){
        List<RatingDto> allRatings = this.ratingService.getAllRatings();
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("all rating").data(allRatings).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    private ResponseEntity<ApiResponse> getAllRatingFail(Exception ex){
        log.info("getAllRatingFail breaker called.....");
        ApiResponse getAllRatingFail = ApiResponse.builder().status(false).serviceName("user-service").message("getAllRatingFail").data(null).build();
        return new ResponseEntity<>(getAllRatingFail, HttpStatus.OK);
    }

    @GetMapping("/rating/user")
    @CircuitBreaker(name = "ratingServiceBreaker", fallbackMethod = "getRatingByUserFail")
    public ResponseEntity<ApiResponse> getRatingByUser(
            @RequestParam(value = "userId", required = true, defaultValue = "1") int userId
    ){
        List<RatingDto> allRatings = this.ratingService.getRatingByUser(userId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("all rating by user").data(allRatings).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    private ResponseEntity<ApiResponse> getRatingByUserFail(
            int userId, Exception ex
    ){
        log.info("getRatingByUserFail breaker called.....");
        ApiResponse getRatingByUserFail = ApiResponse.builder().status(true).serviceName("user-service").message("agetRatingByUserFail").data(null).build();
        return new ResponseEntity<>(getRatingByUserFail, HttpStatus.OK);
    }

    int retry = 1;
    @GetMapping("/rating/hotel")
    @Retry(name = "userRatingRetry", fallbackMethod = "getAllRatingbyHotelFail")
    public ResponseEntity<ApiResponse> getAllRatingbyHotel(
            @RequestParam(value = "hotelId", required = true ,defaultValue = "1") int hotelId
    ){
        log.info("retry : {}", retry);
        retry++;
        List<RatingDto> allRatings = this.ratingService.getRatingByHotel(hotelId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("all rating by hotel").data(allRatings).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    private ResponseEntity<ApiResponse> getAllRatingbyHotelFail(
            int hotelId, Exception ex
    ){
        ApiResponse getAllRatingbyHotelFail = ApiResponse.builder().status(false).serviceName("user-service").message("getAllRatingbyHotelFail").data(null).build();
        return new ResponseEntity<>(getAllRatingbyHotelFail, HttpStatus.OK);
    }

    @GetMapping("/rating/{ratingId}")
    public ResponseEntity<ApiResponse> getRatingById(@PathVariable("ratingId") int ratingId){
        RatingDto byRatingId = this.ratingService.getByRatingId(ratingId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("rating by id").data(byRatingId).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    @PutMapping("/rating/{ratingId}")
    public ResponseEntity<ApiResponse> updateRating(@RequestBody RatingDto ratingDto ,@PathVariable("ratingId") int ratingId){
        RatingDto byRatingId = this.ratingService.updateRating(ratingDto,ratingId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("update rating").data(byRatingId).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    @DeleteMapping("/rating/{ratingId}")
    public ResponseEntity<ApiResponse> deleteRating(@PathVariable("ratingId") int ratingId){
        String s = this.ratingService.deleteRating(ratingId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("delete rating").data(s).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }
    
}
