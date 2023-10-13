package com.user.microservice.controller;

import com.user.microservice.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yyy")
public class YYYController {

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getYYY(){
        ApiResponse build = ApiResponse.builder().serviceName("user-service").message("yyy-message").build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }
}
