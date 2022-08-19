package com.example.domain.feignFindPlaceAPI.service;

import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.model.place.PlaceFindRequest;
import com.example.domain.countryAbbr.CountryNameAbbr;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import com.example.domain.feignFindPlaceAPI.model.Root;
import feign.FeignException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RestApiFindPlaceService {

    private final FeignClientFindPlace findPlace;
    private final Environment env;

    private final CountryNameAbbr countryNameAbbr;

    public RestApiFindPlaceService(FeignClientFindPlace findPlace, Environment env, CountryNameAbbr countryNameAbbr) {
        this.findPlace = findPlace;
        this.env = env;
        this.countryNameAbbr = countryNameAbbr;
    }

    public Place findPlace(PlaceFindRequest placeRequest) {
        try {
            Root foundPlace = findPlace
                    .findPlace(
                            env.getProperty("api.findPlaceHeaderApiKey"),
                            env.getProperty("api.findPlaceHeaderApiHost"),
                            placeRequest.getCity(),
                            countryNameAbbr.getCountryAbbreviation(placeRequest.getCountry()),
                            1)
                    .get(0);

            return Place.builder()
                    .city(foundPlace.getName())
                    .country(foundPlace.getCountry().name)
                    .latitude(foundPlace.getCoordinates().getLatitude())
                    .longitude(foundPlace.getCoordinates().getLongitude())
                    .name(foundPlace.getName() + " Center")
                    .build();

        } catch (Exception ex){
            if(ex instanceof FeignException.BadRequest){
                throw new PlaceNotFoundException("Place not found.");
            }
            if(ex instanceof NullPointerException) {
                throw new PlaceNotFoundException("Place not found.");
            }
            throw new RuntimeException();
        }

    }
}
