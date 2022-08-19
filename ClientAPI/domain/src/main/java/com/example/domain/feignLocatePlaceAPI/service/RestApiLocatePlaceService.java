package com.example.domain.feignLocatePlaceAPI.service;

import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.api.model.place.Coordinate;
import com.example.domain.feignLocatePlaceAPI.model.LocalizedPlace;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import feign.FeignException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class RestApiLocatePlaceService {
    private final Environment env;
    private final FeignClientLocatePlace feignClientPlace;

    public RestApiLocatePlaceService(Environment env, FeignClientLocatePlace feignClientWeather) {
        this.env = env;
        this.feignClientPlace = feignClientWeather;
    }

    public Place getPlace(Coordinate coords) {
        try {
            LocalizedPlace root = feignClientPlace
                    .locatePlace(env.getProperty("api.latLonLocationApiKey"), coords.toString());

            return Place.builder()
                    .city(root.getLocation().getRegion())
                    .country(root.getLocation().getCountry())
                    .latitude(root.getLocation().getLat())
                    .longitude(root.getLocation().getLon())
                    .name(root.getLocation().getName())
                    .build();
        } catch (Exception ex){
            if(ex instanceof FeignException.BadRequest){
                throw new PlaceNotFoundException("Place with latitude: " + coords.getLatitude()
                        + " and longitude " + coords.getLongitude() + " not found.");
            }
            throw new RuntimeException();
        }
    }
}
