package com.example.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Cart is empty")
public class CartIsEmptyException extends RuntimeException{
    public CartIsEmptyException(String message){
        super(message);
    }
}
