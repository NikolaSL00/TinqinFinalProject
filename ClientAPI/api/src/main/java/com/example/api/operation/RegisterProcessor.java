package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.auth.RegisterRequest;
import com.example.api.model.auth.RegisterResponse;
import com.example.api.model.auth.RegisterResult;
import io.vavr.control.Either;

public interface RegisterProcessor extends OperationProcessor<RegisterRequest, RegisterResult> {
    @Override
    Either<Error, RegisterResult> process(RegisterRequest input);
}
