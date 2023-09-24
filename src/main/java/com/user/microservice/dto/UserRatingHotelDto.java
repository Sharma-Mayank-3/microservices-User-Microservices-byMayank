package com.user.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRatingHotelDto {

    private UserDto userDto;

    private List<RatingDto> ratings = new ArrayList<>();

}
