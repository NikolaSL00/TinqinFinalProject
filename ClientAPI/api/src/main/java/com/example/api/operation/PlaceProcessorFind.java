package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.place.PlaceFindRequest;
import com.example.api.model.place.PlaceFindResponse;
import io.vavr.control.Either;

public interface PlaceProcessorFind extends OperationProcessor<PlaceFindRequest, PlaceFindResponse> {
    @Override
    Either<Error, PlaceFindResponse> process(PlaceFindRequest input);
}
