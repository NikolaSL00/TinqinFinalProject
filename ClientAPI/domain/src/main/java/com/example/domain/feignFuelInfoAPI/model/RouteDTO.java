package com.example.domain.feignFuelInfoAPI.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDTO {
    private String from;
    private String to;

    private Double highwayDistance;
    private Double railwayDistance;
    private Double airDistance;
}
