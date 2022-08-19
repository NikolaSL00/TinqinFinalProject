package com.example.core.processor.route;

import com.example.api.base.Error;
import com.example.api.model.route.RouteTransportPricesCompareRequest;
import com.example.api.model.route.RouteTransportPricesCompareResponse;
import com.example.api.operation.RouteProcessorCompare;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class RouteProcessorCompareImpl implements RouteProcessorCompare {

    @Override
    public Either<Error, RouteTransportPricesCompareResponse> process(RouteTransportPricesCompareRequest input) {
        return null;
    }
}
