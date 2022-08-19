package com.example.rest.controller;

import com.example.api.base.Error;
import com.example.api.model.place.Coordinate;
import com.example.api.model.place.PlaceFindRequest;
import com.example.api.model.place.PlaceFindResponse;
import com.example.api.model.place.PlaceLocateResponse;
import com.example.api.operation.PlaceProcessorFind;
import com.example.api.operation.PlaceProcessorLocate;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceProcessorLocate placeProcessorLocate;
    private final PlaceProcessorFind placeProcessorFind;

    public PlaceController(PlaceProcessorLocate placeProcessorLocate, PlaceProcessorFind placeProcessorFind) {
        this.placeProcessorLocate = placeProcessorLocate;
        this.placeProcessorFind = placeProcessorFind;
    }

    @GetMapping("/current")
    ResponseEntity<?> locateCurrentPlace(@RequestBody Coordinate coordinates) {
        Either<Error, PlaceLocateResponse> response = placeProcessorLocate.process(coordinates);

        if(response.isLeft()){
            return ResponseEntity
                    .status(response.getLeft().getCode())
                    .body(response.getLeft().getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.get());
    }

    @GetMapping("/find")
    ResponseEntity<?> findPlace(@RequestBody PlaceFindRequest placeFindRequest) {
        Either<Error, PlaceFindResponse> response = placeProcessorFind.process(placeFindRequest);

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
