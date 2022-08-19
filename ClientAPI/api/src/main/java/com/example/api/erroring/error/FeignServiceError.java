package com.example.api.erroring.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class FeignServiceError implements Error {
    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    @Override
    public String getMessage() {
        return "UserStore Service is not available!";
    }
}
