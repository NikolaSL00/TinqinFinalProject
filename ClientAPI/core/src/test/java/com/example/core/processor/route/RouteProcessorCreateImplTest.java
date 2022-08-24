package com.example.core.processor.route;

import com.example.api.base.Error;
import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.model.place.Coordinate;
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
import com.example.domain.feignLocatePlaceAPI.model.Place;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RouteProcessorCreateImplTest {
    private RouteProcessorCreate toTest;
    @Mock
    private RouteCRUD routeCRUD;
    @Mock
    private PlaceCRUD placeCRUD;
    @Mock
    private GoogleAPIRouteInfoService googleAPIRouteInfoService;

    @BeforeEach
    void setUp() {
        toTest = new RouteProcessorCreateImpl(
                routeCRUD,
                placeCRUD,
                googleAPIRouteInfoService
        );
    }

    @Test
    void createRouteWithAlreadyCreatedPlacesShouldBeSuccessful() {
        // arrange
        RouteCreateRequest testReq = RouteCreateRequest.builder()
                .firstDestination(Coordinate
                        .builder()
                        .city("Plovdiv")
                        .country("Bulgaria")
                        .latitude(42.1354d)
                        .longitude(24.7453d)
                        .build())
                .secondDestination(Coordinate
                        .builder()
                        .city("Varna")
                        .country("Bulgaria")
                        .latitude(43.2141d)
                        .longitude(27.9147d)
                        .build())
                .build();

        RouteCreateResponse expectedResult = RouteCreateResponse.builder()
                .from(PlaceDTO
                        .builder()
                        .city("Plovdiv")
                        .county("Bulgaria")
                        .build())
                .to(PlaceDTO
                        .builder()
                        .city("Varna")
                        .county("Bulgaria")
                        .build())
                .highwayDistance(324760.48d)
                .railwayDistance(369046.0d)
                .airDistance(184523.0d)
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
                .highwayDistance(324760.48d)
                .airDistance(184523.0d)
                .railwayDistance(369046.0d)
                .numSearched(0)
                .build();

        when(placeCRUD.getPlace(any()))
                .thenReturn(Optional.of(startingPlace))
                .thenReturn(Optional.of(targetPlace));

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.empty());

        when(googleAPIRouteInfoService.routeInfo(any(), any()))
                .thenReturn(RouteDTO.builder()
                        .duration(15350)
                        .destinationAddress("Plovdiv, Bulgaria")
                        .originAddress(null)
                        .airDistance(184523.0d)
                        .highwayDistance(324760.48d)
                        .railwayDistance(369046.0d)
                        .build());

        // act
        Either<Error, RouteCreateResponse> testRes = toTest.process(testReq);

        // assert

        assertEquals(
                expectedResult.getFrom(),
                testRes.get().getFrom());

        assertEquals(
                expectedResult.getTo(),
                testRes.get().getTo());

        assertEquals(
                expectedResult.getAirDistance(),
                testRes.get().getAirDistance());

        assertEquals(
                expectedResult.getHighwayDistance(),
                testRes.get().getHighwayDistance());

        assertEquals(
                expectedResult.getRailwayDistance(),
                testRes.get().getRailwayDistance());
    }

    @Test
    void createRouteWithNoCreatedShouldBeSuccessful() {
        RouteCreateRequest testReq = RouteCreateRequest.builder()
                .firstDestination(Coordinate
                        .builder()
                        .city("Plovdiv")
                        .country("Bulgaria")
                        .latitude(42.1354d)
                        .longitude(24.7453d)
                        .build())
                .secondDestination(Coordinate
                        .builder()
                        .city("Varna")
                        .country("Bulgaria")
                        .latitude(43.2141d)
                        .longitude(27.9147d)
                        .build())
                .build();

        RouteCreateResponse expectedResult = RouteCreateResponse.builder()
                .from(PlaceDTO
                        .builder()
                        .city("Plovdiv")
                        .county("Bulgaria")
                        .build())
                .to(PlaceDTO
                        .builder()
                        .city("Varna")
                        .county("Bulgaria")
                        .build())
                .highwayDistance(324760.48d)
                .railwayDistance(369046.0d)
                .airDistance(184523.0d)
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
                .highwayDistance(324760.48d)
                .airDistance(184523.0d)
                .railwayDistance(369046.0d)
                .numSearched(0)
                .build();

        when(placeCRUD.getPlace(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());

        when(placeCRUD.createPlace(any()))
                .thenReturn(startingPlace)
                .thenReturn(targetPlace);

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.empty());

        when(googleAPIRouteInfoService.routeInfo(any(), any()))
                .thenReturn(RouteDTO.builder()
                        .duration(15350)
                        .destinationAddress("Plovdiv, Bulgaria")
                        .originAddress(null)
                        .airDistance(184523.0d)
                        .highwayDistance(324760.48d)
                        .railwayDistance(369046.0d)
                        .build());

        // act
        Either<Error, RouteCreateResponse> testRes = toTest.process(testReq);

        // assert

        assertEquals(
                expectedResult.getFrom(),
                testRes.get().getFrom());

        assertEquals(
                expectedResult.getTo(),
                testRes.get().getTo());

        assertEquals(
                expectedResult.getAirDistance(),
                testRes.get().getAirDistance());

        assertEquals(
                expectedResult.getHighwayDistance(),
                testRes.get().getHighwayDistance());

        assertEquals(
                expectedResult.getRailwayDistance(),
                testRes.get().getRailwayDistance());
    }

    @Test
    void createRouteWithWhenAPIisDownShouldReturnError() {
        RouteCreateRequest testReq = RouteCreateRequest.builder()
                .firstDestination(Coordinate
                        .builder()
                        .city("Plovdiv")
                        .country("Bulgaria")
                        .latitude(42.1354d)
                        .longitude(24.7453d)
                        .build())
                .secondDestination(Coordinate
                        .builder()
                        .city("Varna")
                        .country("Bulgaria")
                        .latitude(43.2141d)
                        .longitude(27.9147d)
                        .build())
                .build();

        RouteCreateResponse expectedResult = RouteCreateResponse.builder()
                .from(PlaceDTO
                        .builder()
                        .city("Plovdiv")
                        .county("Bulgaria")
                        .build())
                .to(PlaceDTO
                        .builder()
                        .city("Varna")
                        .county("Bulgaria")
                        .build())
                .highwayDistance(324760.48d)
                .railwayDistance(369046.0d)
                .airDistance(184523.0d)
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
                .highwayDistance(324760.48d)
                .airDistance(184523.0d)
                .railwayDistance(369046.0d)
                .numSearched(0)
                .build();

        when(placeCRUD.getPlace(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());

        when(placeCRUD.createPlace(any()))
                .thenReturn(startingPlace)
                .thenReturn(targetPlace);

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.empty());

        when(googleAPIRouteInfoService.routeInfo(any(), any()))
                .thenThrow(new PlaceNotFoundException("Bad request."));

        // act
        Either<Error, RouteCreateResponse> testRes = toTest.process(testReq);

        // assert
        assertEquals(
                "UserStore Service is not available!",
                testRes.getLeft().getMessage());

        assertEquals(
                HttpStatus.SERVICE_UNAVAILABLE,
                testRes.getLeft().getCode());
    }

    @Test
    void createRouteWhenThatRouteAlreadyExistsShouldReturnError() {
        RouteCreateRequest testReq = RouteCreateRequest.builder()
                .firstDestination(Coordinate
                        .builder()
                        .city("Plovdiv")
                        .country("Bulgaria")
                        .latitude(42.1354d)
                        .longitude(24.7453d)
                        .build())
                .secondDestination(Coordinate
                        .builder()
                        .city("Varna")
                        .country("Bulgaria")
                        .latitude(43.2141d)
                        .longitude(27.9147d)
                        .build())
                .build();

        RouteCreateResponse expectedResult = RouteCreateResponse.builder()
                .from(PlaceDTO
                        .builder()
                        .city("Plovdiv")
                        .county("Bulgaria")
                        .build())
                .to(PlaceDTO
                        .builder()
                        .city("Varna")
                        .county("Bulgaria")
                        .build())
                .highwayDistance(324760.48d)
                .railwayDistance(369046.0d)
                .airDistance(184523.0d)
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
                .highwayDistance(324760.48d)
                .airDistance(184523.0d)
                .railwayDistance(369046.0d)
                .numSearched(0)
                .build();

        when(placeCRUD.getPlace(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());

        when(placeCRUD.createPlace(any()))
                .thenReturn(startingPlace)
                .thenReturn(targetPlace);

        when(routeCRUD.getRouteByStartingAndEndingPlace(any(), any()))
                .thenReturn(Optional.of(routeEntity));

        when(googleAPIRouteInfoService.routeInfo(any(), any()))
                .thenReturn(RouteDTO.builder()
                        .duration(15350)
                        .destinationAddress("Plovdiv, Bulgaria")
                        .originAddress(null)
                        .airDistance(184523.0d)
                        .highwayDistance(324760.48d)
                        .railwayDistance(369046.0d)
                        .build());

        // act
        Either<Error, RouteCreateResponse> testRes = toTest.process(testReq);

        // assert

        assertEquals(
                "Route already exists.",
                testRes.getLeft().getMessage());

        assertEquals(
                HttpStatus.CONFLICT,
                testRes.getLeft().getCode());
    }

}