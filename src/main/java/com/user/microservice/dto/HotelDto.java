package com.user.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {

    private int hotelId;

    private String hotelName;
    private String hotelLocation;
    private String hotelAbout;
}
