package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.route.RouteCreateRequest;
import com.example.api.model.route.RouteCreateResponse;
import io.vavr.control.Either;

public interface RouteProcessorCreate extends OperationProcessor<RouteCreateRequest, RouteCreateResponse> {
    @Override
    Either<Error, RouteCreateResponse> process(RouteCreateRequest input);
}
