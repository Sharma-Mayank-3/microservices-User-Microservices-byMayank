package com.user.microservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<ApiResponse> restTemplateException(RestTemplateException ex) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse apiResponse = objectMapper.readValue(ex.getMessage().substring(4), ApiResponse.class);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
