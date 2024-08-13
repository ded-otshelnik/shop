package com.example.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CartIsEmptyException extends RuntimeException{
    public CartIsEmptyException(String message){
        super(message);
    }
}
