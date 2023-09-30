package com.user.microservice.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.microservice.dto.RatingDto;
import com.user.microservice.external.clients.RatingFeingClient;
import com.user.microservice.payload.ApiResponse;
import com.user.microservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingFeingClient ratingFeingClient;

    @Override
    public RatingDto createRating(RatingDto ratingDto) {
        ResponseEntity<ApiResponse> rating = this.ratingFeingClient.createRating(ratingDto);
        Object data = rating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        RatingDto ratingDto1 = objectMapper.convertValue(data, RatingDto.class);
        return ratingDto1;
    }

    @Override
    public List<RatingDto> getAllRatings() {
        ResponseEntity<ApiResponse> allRating = this.ratingFeingClient.getAllRating();
        List<Object> data = (List<Object>)allRating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        List<RatingDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, RatingDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<RatingDto> getRatingByUser(int userid) {
        ResponseEntity<ApiResponse> allRating = this.ratingFeingClient.getRatingByUser(userid);
        List<Object> data = (List<Object>)allRating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        List<RatingDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, RatingDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<RatingDto> getRatingByHotel(int hotelId) {
        ResponseEntity<ApiResponse> allRating = this.ratingFeingClient.getAllRatingbyHotel(hotelId);
        List<Object> data = (List<Object>)allRating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        List<RatingDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, RatingDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public RatingDto getByRatingId(int ratingId) {
        ResponseEntity<ApiResponse> rating = this.ratingFeingClient.getRatingById(ratingId);
        Object data = rating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        RatingDto ratingDto = objectMapper.convertValue(data, RatingDto.class);
        return ratingDto;
    }

    @Override
    public RatingDto updateRating(RatingDto ratingDto, int ratingId) {
        ResponseEntity<ApiResponse> rating = this.ratingFeingClient.updateRating(ratingDto, ratingId);
        Object data = rating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        RatingDto ratingDto1 = objectMapper.convertValue(data, RatingDto.class);
        return ratingDto1;
    }

    @Override
    public String deleteRating(int ratingId) {
        ResponseEntity<ApiResponse> rating = this.ratingFeingClient.deleteRating(ratingId);
        Object data = rating.getBody().getData();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.convertValue(data, String.class);
        return s;
    }
}
