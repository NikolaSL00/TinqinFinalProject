package com.example.domain.feignFuelInfoAPI.service;

import com.example.domain.feignFuelInfoAPI.model.FuelInfo;
import com.example.domain.feignFuelInfoAPI.model.RouteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeignClientFuelInfoAPIImpl {

    private final FeignClientFuelInfoAPI feignClientFuelInfoAPI;

    public FeignClientFuelInfoAPIImpl(FeignClientFuelInfoAPI feignClientFuelInfoAPI) {
        this.feignClientFuelInfoAPI = feignClientFuelInfoAPI;
    }

    public FuelInfo getFuelInfoForRoute (RouteDTO routeDTO) {
        try {
            ResponseEntity<FuelInfo> result = feignClientFuelInfoAPI.getFuelInfo(routeDTO);
            return result.getBody();
        } catch (Exception ex) {
            throw new RuntimeException();
        }

    }
}
