package com.example.rest.controller;

import com.example.api.base.Error;
import com.example.api.model.route.RouteCreateRequest;
import com.example.api.model.route.RouteCreateResponse;
import com.example.api.model.route.RouteTransportPricesCompareRequest;
import com.example.api.model.route.RouteTransportPricesCompareResponse;
import com.example.api.operation.RouteProcessorCompare;
import com.example.api.operation.RouteProcessorCreate;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteProcessorCreate routeProcessorCreate;
    private final RouteProcessorCompare routeProcessorCompare;

    public RouteController(RouteProcessorCreate routeProcessor, RouteProcessorCompare routeProcessorCompare) {
        this.routeProcessorCreate = routeProcessor;
        this.routeProcessorCompare = routeProcessorCompare;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoute(@RequestBody RouteCreateRequest request){
        Either<Error, RouteCreateResponse> response = routeProcessorCreate.process(request);

        if(response.isLeft()){
            return ResponseEntity
                    .status(response.getLeft().getCode())
                    .body(response.getLeft().getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.get());
    }

    @GetMapping("/compare")
    public ResponseEntity<?> comparePrices(
            @RequestBody RouteTransportPricesCompareRequest request){
        Either<Error, RouteTransportPricesCompareResponse> response = routeProcessorCompare.process(request);

        if(response.isLeft()){
            return ResponseEntity
                    .status(response.getLeft().getCode())
                    .body(response.getLeft().getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.get());
    }
}
