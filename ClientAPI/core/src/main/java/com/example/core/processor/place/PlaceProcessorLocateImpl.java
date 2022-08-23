package com.example.core.processor.place;

import com.example.api.base.Error;
import com.example.api.erroring.error.FeignServiceError;
import com.example.api.erroring.error.PlaceNotFoundError;
import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.model.place.Coordinate;
import com.example.api.model.place.PlaceLocateRequest;
import com.example.api.model.place.PlaceLocateResponse;
import com.example.api.operation.PlaceProcessorLocate;
import com.example.domain.feignFindPlaceAPI.service.RestApiFindPlaceService;
import com.example.domain.feignGoogleAPI.service.GoogleAPIRouteInfoService;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import com.example.domain.feignLocatePlaceAPI.service.RestApiLocatePlaceService;
import com.example.domain.repository.PlaceEntityRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class PlaceProcessorLocateImpl implements PlaceProcessorLocate {

    private final RestApiLocatePlaceService restApiLocatePlaceService;

    public PlaceProcessorLocateImpl(RestApiLocatePlaceService restApiLocatePlaceService) {
        this.restApiLocatePlaceService = restApiLocatePlaceService;
    }

    @Override
    public Either<Error, PlaceLocateResponse> process(PlaceLocateRequest req) {
        return Try.of(() -> {
                    Place place = restApiLocatePlaceService.getPlace(req);

                    return PlaceLocateResponse
                            .builder()
                            .city(place.getCity())
                            .name(place.getName())
                            .country(place.getCountry())
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
