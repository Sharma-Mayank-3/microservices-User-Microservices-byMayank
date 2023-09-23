package com.user.microservice.controller;

import com.user.microservice.dto.UserDto;
import com.user.microservice.payload.ApiResponse;
import com.user.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/microservices")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserDto userDto){
        UserDto user = this.userService.createUser(userDto);
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("user created").data(user).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserbyId(@PathVariable("userId") int userId){
        UserDto userById = this.userService.getUserById(userId);
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("user created").data(userById).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAllUser(){
        List<UserDto> allUser = this.userService.getAllUser();
        ApiResponse userCreated = ApiResponse.builder().serviceName("user-service").message("user created").data(allUser).status(true).build();
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

}
