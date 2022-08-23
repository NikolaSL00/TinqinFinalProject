package com.example.core.processor.place;

import com.example.api.base.Error;
import com.example.api.model.place.PlaceFindRequest;
import com.example.api.model.place.PlaceFindResponse;
import com.example.api.operation.PlaceProcessorFind;
import com.example.domain.feignFindPlaceAPI.service.RestApiFindPlaceService;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlaceProcessorFindImplTest {

    private PlaceProcessorFind toTest;

    @Mock
    private RestApiFindPlaceService findPlaceService;

    @BeforeEach
    void setUp() {
        toTest = new PlaceProcessorFindImpl(findPlaceService);
    }

    @Test
    void findPlaceWithGoodRequestShouldReturnResponse(){
        // arrange
        PlaceFindRequest testReq = PlaceFindRequest.builder()
                .city("Varna")
                .country("Bulgaria")
                .build();

        PlaceFindResponse expectedRes = PlaceFindResponse
                .builder()
                .longitude(43.2167d)
                .latitude(27.9167d)
                .country("Bulgaria")
                .city("Varna")
                .build();

        Place testPlace = Place.builder()
                .name("Varna Center")
                .city("Varna")
                .country("Bulgaria")
                .latitude(27.9167d)
                .longitude(43.2167d)
                .build();

        when(findPlaceService.findPlace(any()))
                .thenReturn(testPlace);

        // act
        Either<Error, PlaceFindResponse> processRes = toTest.process(testReq);

        // assert
        assertEquals(expectedRes.getCity(),
                processRes.get().getCity());

        assertEquals(expectedRes.getCountry(),
                processRes.get().getCountry());

        assertEquals(expectedRes.getLatitude(),
                processRes.get().getLatitude());

        assertEquals(expectedRes.getLongitude(),
                processRes.get().getLongitude());
    }

    @Test
    void findPlaceWithBadRequestShouldReturnError(){

    }
}