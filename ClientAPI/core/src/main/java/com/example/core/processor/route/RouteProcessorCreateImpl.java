package com.example.core.processor.route;

import com.example.api.base.Error;
import com.example.api.erroring.error.FeignServiceError;
import com.example.api.erroring.error.RouteAlreadyExistsError;
import com.example.api.erroring.exception.RouteAlreadyExistsException;
import com.example.api.model.place.PlaceCreateRequest;
import com.example.api.model.place.PlaceDTO;
import com.example.api.model.route.RouteCreateRequest;
import com.example.api.model.route.RouteCreateResponse;
import com.example.api.operation.RouteProcessorCreate;
import com.example.domain.crud.PlaceCRUD;
import com.example.domain.crud.RouteCRUD;
import com.example.domain.entity.PlaceEntity;
import com.example.domain.entity.RouteEntity;
import com.example.domain.feignGoogleAPI.model.RouteDTO;
import com.example.domain.feignGoogleAPI.service.GoogleAPIRouteInfoService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteProcessorCreateImpl implements RouteProcessorCreate {
    private final RouteCRUD routeCRUD;
    private final PlaceCRUD placeCRUD;
    private final GoogleAPIRouteInfoService googleAPIRouteInfoService;

    public RouteProcessorCreateImpl(RouteCRUD routeCRUD, PlaceCRUD placeCRUD,
                                    GoogleAPIRouteInfoService googleAPIRouteInfoService) {
        this.routeCRUD = routeCRUD;
        this.placeCRUD = placeCRUD;
        this.googleAPIRouteInfoService = googleAPIRouteInfoService;
    }

    @Override
    public Either<Error, RouteCreateResponse> process(RouteCreateRequest request) {
        return Try.of(() -> {
                    PlaceEntity placeDeparture;
                    PlaceEntity placeDestination;

                    Optional<PlaceEntity> placeDepartureOpt = placeCRUD
                            .getPlace(request.getFirstDestination());
                    if (placeDepartureOpt.isEmpty()) {
                        placeDeparture = placeCRUD.createPlace(PlaceCreateRequest
                                .builder()
                                .city(request.getFirstDestination().getCity())
                                .country(request.getFirstDestination().getCountry())
                                .latitude(request.getFirstDestination().getLatitude())
                                .longitude(request.getFirstDestination().getLongitude())
                                .numDeparture(0)
                                .numDestination(0)
                                .build());
                    } else {
                        placeDeparture = placeDepartureOpt.get();
                    }


                    Optional<PlaceEntity> placeDestinationOpt = placeCRUD
                            .getPlace(request.getSecondDestination());
                    if (placeDestinationOpt.isEmpty()) {
                        placeDestination = placeCRUD.createPlace(PlaceCreateRequest
                                .builder()
                                .city(request.getSecondDestination().getCity())
                                .country(request.getSecondDestination().getCountry())
                                .latitude(request.getSecondDestination().getLatitude())
                                .longitude(request.getSecondDestination().getLongitude())
                                .numDeparture(0)
                                .numDestination(0)
                                .build());
                    } else {
                        placeDestination = placeDestinationOpt.get();
                    }

                    routeCRUD.getRouteByStartingAndEndingPlace(placeDeparture, placeDestination)
                            .stream()
                            .findAny()
                            .ifPresent(s -> {
                                throw new RouteAlreadyExistsException("Route already exists");
                            });

                    placeDestination.setNumDestination(placeDestination.getNumDestination() + 1);
                    placeCRUD.updatePlace(placeDestination);

                    placeDeparture.setNumDeparture(placeDeparture.getNumDeparture() + 1);
                    placeCRUD.updatePlace(placeDeparture);

                    RouteDTO routeInfo = googleAPIRouteInfoService
                            .routeInfo(placeDestination, placeDeparture);

                    RouteEntity route = RouteEntity.builder()
                            .startingPlace(placeDeparture)
                            .endPlace(placeDestination)
                            .airDistance(routeInfo.getAirDistance())
                            .railwayDistance(routeInfo.getRailwayDistance())
                            .highwayDistance(routeInfo.getHighwayDistance())
                            .numSearched(0)
                            .build();

                    routeCRUD.createRoute(route);

                    return RouteCreateResponse.builder()
                            .from(PlaceDTO.builder()
                                    .city(placeDeparture.getCity())
                                    .county(placeDeparture.getCountry())
                                    .build())
                            .to(PlaceDTO.builder()
                                    .city(placeDestination.getCity())
                                    .county(placeDestination.getCountry())
                                    .build())
                            .airDistance(route.getAirDistance())
                            .railwayDistance(route.getRailwayDistance())
                            .highwayDistance(route.getHighwayDistance())
                            .build();
                }).toEither()
                .mapLeft(throwable -> {
                    if (throwable instanceof RouteAlreadyExistsException) {
                        return new RouteAlreadyExistsError();
                    }
                    return new FeignServiceError();
                });
    }

}
