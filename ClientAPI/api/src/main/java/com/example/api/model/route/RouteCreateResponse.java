package com.example.api.model.route;

import com.example.api.base.OperationResult;
import com.example.api.model.place.PlaceDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteCreateResponse implements OperationResult {
    private PlaceDTO from;
    private PlaceDTO to;
    private Double railwayDistance;
    private Double highwayDistance;
    private Double airDistance;
}
