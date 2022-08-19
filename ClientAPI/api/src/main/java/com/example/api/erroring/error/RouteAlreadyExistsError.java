package com.example.api.erroring.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class RouteAlreadyExistsError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessage() {
        return "Route already exists.";
    }
}
