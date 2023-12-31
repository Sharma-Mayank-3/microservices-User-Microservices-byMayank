package com.user.microservice.service;

import com.user.microservice.dto.UserDto;
import com.user.microservice.dto.UserRatingHotelDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(int userId);

    List<UserDto> getAllUser();

    UserRatingHotelDto getUserRatingHotel(int userId);

}
