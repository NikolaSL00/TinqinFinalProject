package com.example.core.processor.route;

import com.example.api.base.Error;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteProcessorCompareImplTest {

    private RouteProcessorCompare toTest;

    @Mock
    private FeignClientFuelInfoAPIImpl fuelInfoService;
    @Mock
    private PlaceCRUD placeCRUD;
    @Mock
    private RouteCRUD routeCRUD;

    @BeforeEach
    void setUp() {
        toTest = new RouteProcessorCompareImpl(
                fuelInfoService,
                placeCRUD,
                routeCRUD);
    }

    @Test
    void routeCompareWithProperRequestShouldBeSuccessful() {
        // arrange
        RouteTransportPricesCompareRequest testReq = RouteTransportPricesCompareRequest.builder()
                .from("Plovdiv")
                .to("Varna")
                .build();

        RouteTransportPricesCompareResponse expectedRes = RouteTransportPricesCompareResponse.builder()
                .highwayDistance(369046.0d)
                .carPrice(217.73713999999998d)
                .busPrice(103.33288000000002d)
                .railwayDistance(324760.48d)
                .railwayPrice(194.85628799999998d)
                .airDistance(184523.0d)
                .airPrice(3469.0324d)
                .build();

        PlaceEntity targetPlace = PlaceEntity.builder()
                .city("Varna")
                .country("Bulgaria")
                .name("Varna Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(43.2141d)
                .longitude(27.9147d)
                .build();

        PlaceEntity startingPlace = PlaceEntity.builder()
                .city("Plovdiv")
                .country("Bulgaria")
                .name("Plovdiv Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(42.1354d)
                .longitude(24.7453d)
                .build();

        RouteEntity routeEntity = RouteEntity.builder()
                .startingPlace(startingPlace)
                .endPlace(targetPlace)
                .highwayDistance(369046.0d)
                .airDistance(184523.0d)
                .railwayDistance(324760.48)
                .numSearched(0)
                .build();

        FuelInfo fuelInfo = FuelInfo.builder()
                .from(startingPlace.getCity())
                .to(targetPlace.getCity())
                .busPrice(103.33288000000002d)
                .carPrice(217.73713999999998d)
                .railwayPrice(194.85628799999998d)
                .airlinePrice(3469.0324d)
                .build();

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.of(routeEntity));

        when(placeCRUD.getPlaceByCity(any()))
                .thenReturn(Optional.of(startingPlace))
                .thenReturn(Optional.of(targetPlace));

        when(fuelInfoService.getFuelInfoForRoute(any()))
                .thenReturn(fuelInfo);

        // act
        Either<Error, RouteTransportPricesCompareResponse> testRes = toTest.process(testReq);

        // assert
        assertEquals(
                expectedRes.getAirDistance(),
                testRes.get().getAirDistance());

        assertEquals(
                expectedRes.getAirPrice(),
                testRes.get().getAirPrice());

        assertEquals(
                expectedRes.getBusPrice(),
                testRes.get().getBusPrice());

        assertEquals(
                expectedRes.getHighwayDistance(),
                testRes.get().getHighwayDistance());

        assertEquals(
                expectedRes.getCarPrice(),
                testRes.get().getCarPrice());

        assertEquals(
                expectedRes.getRailwayDistance(),
                testRes.get().getRailwayDistance());

        assertEquals(
                expectedRes.getRailwayPrice(),
                testRes.get().getRailwayPrice());
    }

    @Test
    void routeCompareWithFirstPlaceNotPresentShouldReturnError() {
        // arrange

        RouteTransportPricesCompareRequest testReq = RouteTransportPricesCompareRequest.builder()
                .from("Plovdiv")
                .to("Varna")
                .build();

        when(placeCRUD.getPlaceByCity(any()))
                .thenReturn(Optional.empty());

        // act
        Either<Error, RouteTransportPricesCompareResponse> testRes = toTest.process(testReq);

        // assert
        assertEquals("Place not found.",
                testRes.getLeft().getMessage());

        assertEquals(HttpStatus.NOT_FOUND,
                testRes.getLeft().getCode());
    }

    @Test
    void routeCompareWithSecondPlaceNotPresentShouldReturnError() {
        // arrange

        RouteTransportPricesCompareRequest testReq = RouteTransportPricesCompareRequest.builder()
                .from("Plovdiv")
                .to("Varna")
                .build();

        PlaceEntity startingPlace = PlaceEntity.builder()
                .city("Plovdiv")
                .country("Bulgaria")
                .name("Plovdiv Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(42.1354d)
                .longitude(24.7453d)
                .build();

        PlaceEntity targetPlace = PlaceEntity.builder()
                .city("Varna")
                .country("Bulgaria")
                .name("Varna Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(43.2141d)
                .longitude(27.9147d)
                .build();

        when(placeCRUD.getPlaceByCity(any()))
                .thenReturn(Optional.of(startingPlace))
                .thenReturn(Optional.empty());

        // act
        Either<Error, RouteTransportPricesCompareResponse> testRes = toTest.process(testReq);

        // assert
        assertEquals("Place not found.",
                testRes.getLeft().getMessage());

        assertEquals(HttpStatus.NOT_FOUND,
                testRes.getLeft().getCode());
    }

    @Test
    void routeCompareRouteNotPresentShouldReturnError() {
        // arrange

        RouteTransportPricesCompareRequest testReq = RouteTransportPricesCompareRequest.builder()
                .from("Plovdiv")
                .to("Varna")
                .build();

        PlaceEntity startingPlace = PlaceEntity.builder()
                .city("Plovdiv")
                .country("Bulgaria")
                .name("Plovdiv Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(42.1354d)
                .longitude(24.7453d)
                .build();

        PlaceEntity targetPlace = PlaceEntity.builder()
                .city("Varna")
                .country("Bulgaria")
                .name("Varna Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(43.2141d)
                .longitude(27.9147d)
                .build();

        when(placeCRUD.getPlaceByCity(any()))
                .thenReturn(Optional.of(startingPlace))
                .thenReturn(Optional.of(targetPlace));

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.empty());

        // act
        Either<Error, RouteTransportPricesCompareResponse> testRes = toTest.process(testReq);

        // assert
        assertEquals("Route not found.",
                testRes.getLeft().getMessage());

        assertEquals(HttpStatus.NOT_FOUND,
                testRes.getLeft().getCode());
    }

    @Test
    void routeCompareAPIThrowsExceptionShouldReturnError() {
        // arrange
        RouteTransportPricesCompareRequest testReq = RouteTransportPricesCompareRequest.builder()
                .from("Plovdiv")
                .to("Varna")
                .build();

        PlaceEntity targetPlace = PlaceEntity.builder()
                .city("Varna")
                .country("Bulgaria")
                .name("Varna Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(43.2141d)
                .longitude(27.9147d)
                .build();

        PlaceEntity startingPlace = PlaceEntity.builder()
                .city("Plovdiv")
                .country("Bulgaria")
                .name("Plovdiv Center")
                .numDeparture(0)
                .numDestination(0)
                .latitude(42.1354d)
                .longitude(24.7453d)
                .build();

        RouteEntity routeEntity = RouteEntity.builder()
                .startingPlace(startingPlace)
                .endPlace(targetPlace)
                .highwayDistance(369046.0d)
                .airDistance(184523.0d)
                .railwayDistance(324760.48)
                .numSearched(0)
                .build();

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.of(routeEntity));

        when(placeCRUD.getPlaceByCity(any()))
                .thenReturn(Optional.of(startingPlace))
                .thenReturn(Optional.of(targetPlace));

        when(fuelInfoService.getFuelInfoForRoute(any()))
                .thenThrow(new RuntimeException());

        // act
        Either<Error, RouteTransportPricesCompareResponse> testRes = toTest.process(testReq);

        // assert

        assertEquals(
                "UserStore Service is not available!",
                testRes.getLeft().getMessage()
        );

        assertEquals(
                HttpStatus.SERVICE_UNAVAILABLE,
                testRes.getLeft().getCode()
        );
    }
}