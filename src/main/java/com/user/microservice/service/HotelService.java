package com.user.microservice.service;

import com.user.microservice.dto.HotelDto;

import java.util.List;

public interface HotelService {

    HotelDto createHotel(HotelDto hotelDto);

    HotelDto findHotelById(int hotelId);

    List<HotelDto> getAllHotels();

    HotelDto updateHotel(HotelDto hotelDto, int hotelId);

    String deleteHotel(int hotelId);

}
