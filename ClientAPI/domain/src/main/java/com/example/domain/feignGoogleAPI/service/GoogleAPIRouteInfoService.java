package com.example.domain.feignGoogleAPI.service;

import com.example.api.erroring.exception.PlaceNotFoundException;
import com.example.domain.entity.PlaceEntity;
import com.example.domain.feignGoogleAPI.model.GoogleRootElement;
import com.example.domain.feignGoogleAPI.model.RouteDTO;
import feign.FeignException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class GoogleAPIRouteInfoService {

    private final FeignClientGoogleAPI feignClientGoogleAPI;
    private final Environment env;

    public GoogleAPIRouteInfoService(FeignClientGoogleAPI feignClientGoogleAPI, Environment env) {
        this.feignClientGoogleAPI = feignClientGoogleAPI;
        this.env = env;
    }

    public RouteDTO routeInfo (PlaceEntity origin, PlaceEntity destination) {
        try {
            GoogleRootElement result = feignClientGoogleAPI
                    .getRouteInfo(destination.getCity(),
                            origin.getCity(),
                            env.getProperty("api.googleKey"));

            return RouteDTO.builder()
                    .destinationAddress(result.getDestination_addresses().get(0))
                    .destinationAddress(result.getDestination_addresses().get(0))
                    .highwayDistance((double) result.getRows().get(0).getElements().get(0).getDistance().getValue())
                    .railwayDistance(result.getRows().get(0).getElements().get(0).getDistance().getValue() * Double.parseDouble(env.getProperty("constants.highwayToRailway")))
                    .airDistance(result.getRows().get(0).getElements().get(0).getDistance().getValue() * Double.parseDouble(env.getProperty("constants.highwayToAir")))
                    .duration(result.getRows().get(0).getElements().get(0).getDuration().getValue())
                    .build();

        } catch (Exception ex){
            if(ex instanceof FeignException.BadRequest){
                throw new PlaceNotFoundException("Bad request");
            }
            if(ex instanceof NullPointerException) {
                throw new PlaceNotFoundException("Bad request.");
            }
            throw new RuntimeException();
        }

    }


}
