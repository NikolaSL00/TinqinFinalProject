package com.example.domain.feignGoogleAPI.service;

import com.example.api.erroring.exception.FeignClientException;
import com.example.domain.feignGoogleAPI.model.GoogleRootElement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "googleAPI",
        url = "${api.googleUrl}",
        configuration = FeignClientException.class)
public interface FeignClientGoogleAPI {

    @GetMapping()
    GoogleRootElement getRouteInfo(
            @RequestParam(value = "destinations") String destinations,
            @RequestParam(value = "origins") String origins,
            @RequestParam(value = "key") String key);
}
