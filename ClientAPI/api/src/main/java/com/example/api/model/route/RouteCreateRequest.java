package com.example.api.model.route;


import com.example.api.base.OperationInput;
import com.example.api.model.place.Coordinate;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RouteCreateRequest implements OperationInput {
    private Coordinate firstDestination;
    private Coordinate secondDestination;
}