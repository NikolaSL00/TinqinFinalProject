package com.example.api.erroring.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class RouteNotFoundError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "Route not found.";
    }
}
