package com.user.microservice.controller;

import com.user.microservice.dto.HotelDto;
import com.user.microservice.payload.ApiResponse;
import com.user.microservice.service.HotelService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/microservices/user")
public class UserHotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel")
    public ResponseEntity<ApiResponse> createHotel(@RequestBody HotelDto hotelDto){
        HotelDto hotel = this.hotelService.createHotel(hotelDto);
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("hotel created").data(hotel).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    @GetMapping("/hotel/{hotelId}")
    @CircuitBreaker(name = "hotelServiceBreaker", fallbackMethod = "getHotelByIdFail")
    public ResponseEntity<ApiResponse> getHotelById(@PathVariable("hotelId") int hotelId){
        HotelDto hotel = this.hotelService.findHotelById(hotelId);
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("get hotel by Id").data(hotel).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    private ResponseEntity<ApiResponse> getHotelByIdFail(int hotelId, Exception ex){
        log.error("getHotelByIdFail method called.... : {}", ex.getMessage());
        ApiResponse userByIdFail = ApiResponse.builder().serviceName("user-service").message("userByIdFail").data(null).status(false).build();
        return new ResponseEntity<>(userByIdFail, HttpStatus.OK);
    }

    @GetMapping("/hotel")
    @CircuitBreaker(name = "hotelServiceBreaker", fallbackMethod = "getAllHotelsFail")
    public ResponseEntity<ApiResponse> getAllHotes(){
        List<HotelDto> allHotels = this.hotelService.getAllHotels();
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("all hotels list").data(allHotels).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    private ResponseEntity<ApiResponse> getAllHotelsFail(Exception ex){
        log.info("getAllHotelsFail method called.... : {}", ex.getMessage());
        ApiResponse userFailed = ApiResponse.builder().serviceName("user-service").message("getAllHotelsFail").data(null).status(false).build();
        return new ResponseEntity<>(userFailed, HttpStatus.OK);
    }

    @PutMapping("/hotel/{hotelId}")
    public ResponseEntity<ApiResponse> updateHotel(
            @RequestBody HotelDto hotelDto,
            @PathVariable("hotelId") int hotelId
    ){
        HotelDto hotelDto1 = this.hotelService.updateHotel(hotelDto, hotelId);
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("hotel updated").data(hotelDto1).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    @DeleteMapping("/hotel/{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable("hotelId") int hotelId){
        String s = this.hotelService.deleteHotel(hotelId);
        ApiResponse userDeleted = ApiResponse.builder().serviceName("user-service").message("hotel deleted").data(s).status(true).build();
        return new ResponseEntity<>(userDeleted, HttpStatus.OK);
    }

}
