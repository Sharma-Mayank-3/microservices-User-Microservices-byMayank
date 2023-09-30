package com.user.microservice.external.clients;

import com.user.microservice.dto.RatingDto;
import com.user.microservice.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RATING-SERVICE", url = "${rating-service.basepath}")
public interface RatingFeingClient {

    @PostMapping("/rating")
    ResponseEntity<ApiResponse> createRating(@RequestBody RatingDto ratingDto);

    @GetMapping("/rating")
    ResponseEntity<ApiResponse> getAllRating();

    @GetMapping("/rating/user")
    ResponseEntity<ApiResponse> getRatingByUser(
            @RequestParam(value = "userId", required = true, defaultValue = "1") int userId
    );

    @GetMapping("/rating/hotel")
    ResponseEntity<ApiResponse> getAllRatingbyHotel(
            @RequestParam(value = "hotelId", required = true ,defaultValue = "1") int hotelId
    );

    @GetMapping("/rating/{ratingId}")
    ResponseEntity<ApiResponse> getRatingById(@PathVariable("ratingId") int ratingId);

    @PutMapping("/rating/{ratingId}")
    ResponseEntity<ApiResponse> updateRating(@RequestBody RatingDto ratingDto ,@PathVariable("ratingId") int ratingId);

    @DeleteMapping("/rating/{ratingId}")
    ResponseEntity<ApiResponse> deleteRating(@PathVariable("ratingId") int ratingId);

}
