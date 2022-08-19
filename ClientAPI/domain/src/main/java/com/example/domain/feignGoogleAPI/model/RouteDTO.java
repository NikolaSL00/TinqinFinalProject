package com.example.domain.feignGoogleAPI.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDTO {
    private String destinationAddress;
    private String originAddress;
    private Double highwayDistance;
    private Double railwayDistance;
    private Double airDistance;

    private Integer duration;
}
