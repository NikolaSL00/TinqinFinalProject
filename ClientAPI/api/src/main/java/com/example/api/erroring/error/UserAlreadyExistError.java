package com.example.api.erroring.error;

import com.example.api.base.Error;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistError implements Error {


    @Override
    public HttpStatus getCode() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessage() {
        return "User with that username is already present!";
    }
}
