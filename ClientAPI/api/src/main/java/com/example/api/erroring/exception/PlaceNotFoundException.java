package com.example.api.erroring.exception;

public class PlaceNotFoundException extends RuntimeException {
    private final String message;
    public PlaceNotFoundException(String message) {
        this.message = message;
    }

}
