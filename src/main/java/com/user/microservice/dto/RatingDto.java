package com.user.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private int ratingId;

    private int userId;
    private int hotelId;
    private int rating;
    private String feedback;

    private HotelDto hotelDto;
}
