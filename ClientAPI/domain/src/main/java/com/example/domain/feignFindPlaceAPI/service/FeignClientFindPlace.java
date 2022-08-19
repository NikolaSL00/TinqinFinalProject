package com.example.domain.feignFindPlaceAPI.service;

import com.example.api.erroring.exception.FeignClientException;
import com.example.domain.feignFindPlaceAPI.model.Root;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@FeignClient(name = "placeFind",
        url = "${api.findPlaceApiUrl}",
        configuration = FeignClientException.class)
public interface FeignClientFindPlace {

    @GetMapping()
    ArrayList<Root> findPlace(
            @RequestHeader("X-RapidAPI-Key") String key,
            @RequestHeader("X-RapidAPI-Host") String host,
            @RequestParam(value = "q") String city,
            @RequestParam(value = "country") String country,
            @RequestParam(value = "limit") Integer limit);
}