package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.auth.LoginRequest;
import com.example.api.model.auth.LoginResponse;
import com.example.api.model.auth.LoginResult;
import io.vavr.control.Either;

public interface LoginProcessor extends OperationProcessor<LoginRequest, LoginResult> {
    @Override
    Either<Error, LoginResult> process(LoginRequest input);
}
