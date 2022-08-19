package com.example.core.processor.place;

import com.example.api.base.Error;
import com.example.api.erroring.error.FeignServiceError;
import com.example.api.erroring.error.PlaceNotFoundError;
import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.model.place.PlaceFindRequest;
import com.example.api.model.place.PlaceFindResponse;
import com.example.api.operation.PlaceProcessorFind;
import com.example.domain.feignFindPlaceAPI.service.RestApiFindPlaceService;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class PlaceProcessorFindImpl implements PlaceProcessorFind {

    private final RestApiFindPlaceService restApiFindPlaceService;

    public PlaceProcessorFindImpl(RestApiFindPlaceService restApiFindPlaceService) {
        this.restApiFindPlaceService = restApiFindPlaceService;
    }

    @Override
    public Either<Error, PlaceFindResponse> process(PlaceFindRequest input) {
        return Try.of(() -> {
                    Place place = restApiFindPlaceService.findPlace(input);

                    return PlaceFindResponse
                            .builder()
                            .city(place.getCity())
                            .country(place.getCountry())
                            .latitude(place.getLatitude())
                            .longitude(place.getLongitude())
                            .build();
                }).toEither()
                .mapLeft(throwable -> {
                    if (throwable instanceof PlaceNotFoundException) {
                        return new PlaceNotFoundError();
                    }
                    return new FeignServiceError();
                });
    }
}
