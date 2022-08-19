package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.base.OperationProcessor;
import com.example.api.model.place.Coordinate;
import com.example.api.model.place.PlaceLocateResponse;
import io.vavr.control.Either;

public interface PlaceProcessorLocate extends OperationProcessor<Coordinate, PlaceLocateResponse> {
    @Override
    Either<Error, PlaceLocateResponse> process(Coordinate coords);
}
