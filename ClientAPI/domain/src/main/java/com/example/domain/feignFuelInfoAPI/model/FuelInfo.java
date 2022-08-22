package com.example.domain.feignFuelInfoAPI.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuelInfo {
    private String from;
    private String to;

    private Double busPrice;
    private Double carPrice;
    private Double railwayPrice;
    private Double airlinePrice;
}
