package com.user.microservice.controller;

import com.user.microservice.dto.RatingDto;
import com.user.microservice.payload.ApiResponse;
import com.user.microservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<ApiResponse> getAllRating(){
        List<RatingDto> allRatings = this.ratingService.getAllRatings();
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("all rating").data(allRatings).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    @GetMapping("/rating/user")
    public ResponseEntity<ApiResponse> getRatingByUser(
            @RequestParam(value = "userId", required = true, defaultValue = "1") int userId
    ){
        List<RatingDto> allRatings = this.ratingService.getRatingByUser(userId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("all rating by user").data(allRatings).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
    }

    @GetMapping("/rating/hotel")
    public ResponseEntity<ApiResponse> getAllRatingbyHotel(
            @RequestParam(value = "hotelId", required = true ,defaultValue = "1") int hotelId
    ){
        List<RatingDto> allRatings = this.ratingService.getRatingByHotel(hotelId);
        ApiResponse ratingCreated = ApiResponse.builder().status(true).serviceName("user-service").message("all rating by hotel").data(allRatings).build();
        return new ResponseEntity<>(ratingCreated, HttpStatus.OK);
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
