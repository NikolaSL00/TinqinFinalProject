package com.example.domain.feignFuelInfoAPI.service;


import com.example.api.erroring.exception.FeignClientException;
import com.example.domain.feignFuelInfoAPI.model.FuelInfo;
import com.example.domain.feignFuelInfoAPI.model.RouteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fuelAPI",
        url = "${fuelInfo.url}",
        configuration = FeignClientException.class)
public interface FeignClientFuelInfoAPI {

    @GetMapping("/fuelInfo")
    ResponseEntity<FuelInfo> getFuelInfo(@RequestParam RouteDTO route);

}
