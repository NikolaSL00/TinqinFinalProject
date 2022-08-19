package com.example.api.erroring.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserCreationFailureException extends RuntimeException{
    public UserCreationFailureException(String message) {
        super(message);
    }
}
