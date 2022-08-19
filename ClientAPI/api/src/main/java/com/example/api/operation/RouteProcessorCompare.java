package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.route.RouteTransportPricesCompareRequest;
import com.example.api.model.route.RouteTransportPricesCompareResponse;
import io.vavr.control.Either;

public interface RouteProcessorCompare extends OperationProcessor<RouteTransportPricesCompareRequest, RouteTransportPricesCompareResponse> {
    @Override
    Either<Error, RouteTransportPricesCompareResponse> process(RouteTransportPricesCompareRequest input);
}
