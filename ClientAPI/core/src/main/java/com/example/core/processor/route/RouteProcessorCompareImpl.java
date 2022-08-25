package com.example.core.processor.route;

import com.example.api.base.Error;
import com.example.api.erroring.error.FeignServiceError;
import com.example.api.erroring.error.PlaceNotFoundError;
import com.example.api.erroring.error.RouteNotFoundError;
import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.erroring.exception.RouteNotFoundException;
import com.example.api.model.route.RouteTransportPricesCompareRequest;
import com.example.api.model.route.RouteTransportPricesCompareResponse;
import com.example.api.operation.RouteProcessorCompare;
import com.example.domain.crud.PlaceCRUD;
import com.example.domain.crud.RouteCRUD;
import com.example.domain.entity.PlaceEntity;
import com.example.domain.entity.RouteEntity;
import com.example.domain.feignFuelInfoAPI.model.FuelInfo;
import com.example.domain.feignFuelInfoAPI.model.RouteDTO;
import com.example.domain.feignFuelInfoAPI.service.FeignClientFuelInfoAPIImpl;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteProcessorCompareImpl implements RouteProcessorCompare {

    private final FeignClientFuelInfoAPIImpl fuelInfoService;
    private final PlaceCRUD placeCRUD;
    private final RouteCRUD routeCRUD;

    public RouteProcessorCompareImpl(FeignClientFuelInfoAPIImpl fuelInfoService, PlaceCRUD placeCRUD, RouteCRUD routeCRUD) {
        this.fuelInfoService = fuelInfoService;
        this.placeCRUD = placeCRUD;
        this.routeCRUD = routeCRUD;
    }

    @Override
    public Either<Error, RouteTransportPricesCompareResponse> process(RouteTransportPricesCompareRequest input) {
        return Try.of(() -> {
                    Optional<PlaceEntity> startingPlace = placeCRUD
                            .getPlaceByCity(input.getFrom());
                    if (startingPlace.isEmpty()) {
                        throw new PlaceNotFoundException("Starting Place " + input.getFrom() + " not found!");
                    }

                    Optional<PlaceEntity> endPlace = placeCRUD
                            .getPlaceByCity(input.getTo());
                    if (endPlace.isEmpty()) {
                        throw new PlaceNotFoundException("Destination Place " + input.getTo() + " not found!");
                    }

                    Optional<RouteEntity> routeOpt = routeCRUD
                            .getRouteByStartingAndEndingPlace(startingPlace.get(), endPlace.get());
                    if (routeOpt.isEmpty()) {
                        throw new RouteNotFoundException("Route not found");
                    }

                    RouteDTO req =
                            RouteDTO.builder()
                            .from(startingPlace.get().getCity())
                            .to(endPlace.get().getCity())
                            .highwayDistance(routeOpt.get().getHighwayDistance())
                            .airDistance(routeOpt.get().getAirDistance())
                            .railwayDistance(routeOpt.get().getRailwayDistance())
                            .build();

                    FuelInfo fuelInfoResponse = fuelInfoService.getFuelInfoForRoute(req);


                    routeOpt.get()
                            .setNumSearched(routeOpt.get()
                                    .getNumSearched() + 1);
                    routeCRUD.updateRoute(routeOpt.get());

                    return RouteTransportPricesCompareResponse
                            .builder()
                            .highwayDistance(routeOpt.get().getHighwayDistance())
                            .airDistance(routeOpt.get().getAirDistance())
                            .railwayDistance(routeOpt.get().getRailwayDistance())
                            .carPrice(fuelInfoResponse.getCarPrice())
                            .busPrice(fuelInfoResponse.getBusPrice())
                            .railwayPrice(fuelInfoResponse.getRailwayPrice())
                            .airPrice(fuelInfoResponse.getAirlinePrice())
                            .build();
                }).toEither()
                .mapLeft(throwable -> {
                    if (throwable instanceof PlaceNotFoundException) {
                        return new PlaceNotFoundError();
                    }

                    if (throwable instanceof RouteNotFoundException) {
                        return new RouteNotFoundError();
                    }
                    return new FeignServiceError();
                });
    }
}
