package com.example.core.processor.place;

import com.example.api.base.Error;
import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.model.place.Coordinate;
import com.example.api.model.place.PlaceLocateRequest;
import com.example.api.model.place.PlaceLocateResponse;
import com.example.api.operation.PlaceProcessorLocate;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import com.example.domain.feignLocatePlaceAPI.service.RestApiLocatePlaceService;
import feign.FeignException;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceProcessorLocateImplTest {

    private PlaceProcessorLocate toTest;
    @Mock
    private RestApiLocatePlaceService locatePlaceService;

    @BeforeEach
    void setUp(){
        toTest = new PlaceProcessorLocateImpl(locatePlaceService);
    }

    @Test
    void locatePlaceWithGoodRequestShouldSuccessfulResponse(){
        // arrange
        PlaceLocateRequest testReq = PlaceLocateRequest.builder()
                .latitude(40d)
                .longitude(40d)
                .build();

        Place place = Place.builder()
                .longitude(40d)
                .latitude(40d)
                .city("Erzincan")
                .name("Karakulak")
                .country("Turkey")
                .build();

        PlaceLocateResponse expectedRes = PlaceLocateResponse.builder()
                .city("Erzincan")
                .country("Turkey")
                .name("Karakulak")
                .build();

        when(locatePlaceService.getPlace(testReq))
                .thenReturn(place);

        // act
        Either<Error, PlaceLocateResponse> processRes = toTest.process(testReq);

        // assert
        assertEquals(expectedRes.getCity(),
                processRes.get().getCity());

        assertEquals(expectedRes.getCountry(),
                processRes.get().getCountry());

        assertEquals(expectedRes.getName(),
                processRes.get().getName());
    }
    @Test
    void locatePlaceWithBadRequestShouldReturnError(){
        // arrange
        PlaceLocateRequest testReq = PlaceLocateRequest.builder()
                .latitude(40d)
                .longitude(40d)
                .build();

        Place place = Place.builder()
                .longitude(40d)
                .latitude(40d)
                .city("Erzincan")
                .name("Karakulak")
                .country("Turkey")
                .build();

        PlaceLocateResponse expectedRes = PlaceLocateResponse.builder()
                .city("Erzincan")
                .country("Turkey")
                .name("Karakulak")
                .build();

        when(locatePlaceService.getPlace(testReq))
                .thenThrow(new PlaceNotFoundException("Place with latitude: " + testReq.getLatitude()
                        + " and longitude " + testReq.getLongitude() + " not found."));

        // act
        Either<Error, PlaceLocateResponse> processRes = toTest.process(testReq);

        // assert
        assertEquals(HttpStatus.NOT_FOUND,
                processRes.getLeft().getCode());

        assertEquals("Place not found.",
                processRes.getLeft().getMessage());
    }
    @Test
    void locatePlaceWithServerErrorShouldReturnError(){
        // arrange
        PlaceLocateRequest testReq = PlaceLocateRequest.builder()
                .latitude(40d)
                .longitude(40d)
                .build();

        Place place = Place.builder()
                .longitude(40d)
                .latitude(40d)
                .city("Erzincan")
                .name("Karakulak")
                .country("Turkey")
                .build();

        PlaceLocateResponse expectedRes = PlaceLocateResponse.builder()
                .city("Erzincan")
                .country("Turkey")
                .name("Karakulak")
                .build();

        when(locatePlaceService.getPlace(testReq))
                .thenThrow(new RuntimeException());

        // act
        Either<Error, PlaceLocateResponse> processRes = toTest.process(testReq);

        // assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE,
                processRes.getLeft().getCode());

        assertEquals("UserStore Service is not available!",
                processRes.getLeft().getMessage());
    }
}