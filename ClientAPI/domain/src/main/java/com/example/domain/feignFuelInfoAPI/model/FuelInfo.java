package com.example.domain.feignFuelInfoAPI.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuelInfo {
    private Double vehiclePrice;
    private Double railwayPrice;
    private Double airlinePrice;
}
