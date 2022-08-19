package com.example.api.operation;

import com.example.api.base.Error;
import com.example.api.model.place.*;
import io.vavr.control.Either;

public interface PlaceProcessor {
    Either<Error, PlaceLocateResponse> locatePlace(Coordinate coords);
    Either<Error, PlaceFindResponse> findPlace(PlaceFindRequest placeFindRequest);
}
