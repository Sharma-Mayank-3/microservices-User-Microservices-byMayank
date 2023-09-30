package com.user.microservice.exception;

public class RestTemplateException extends RuntimeException{

    public RestTemplateException(String message) {
        super(message);
    }
}
