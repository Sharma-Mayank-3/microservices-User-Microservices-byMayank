package com.user.microservice.exception;

import com.user.microservice.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
        ApiResponse build = ApiResponse.builder().status(false).message(ex.getMessage()).serviceName("user-service").build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

}
