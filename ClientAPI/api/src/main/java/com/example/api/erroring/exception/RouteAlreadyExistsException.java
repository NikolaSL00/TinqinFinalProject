package com.example.api.erroring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RouteAlreadyExistsException extends RuntimeException{

    public RouteAlreadyExistsException(String message) {
        super(message);
    }
}
