package com.example.domain.feignLocatePlaceAPI.service;

import com.example.api.erroring.exception.FeignClientException;
import com.example.domain.feignLocatePlaceAPI.model.LocalizedPlace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "placeLocate", url = "${api.latLonLocationBaseUrl}", configuration = FeignClientException.class)
public interface FeignClientLocatePlace {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    LocalizedPlace locatePlace(@RequestParam(value = "key") String key,
                               @RequestParam(value = "q") String coordsParam);
}
