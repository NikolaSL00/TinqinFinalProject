package com.example.api.model.route;

import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteTransportPricesCompareResponse implements OperationResult {
    private Double highwayDistance;
    private Double carPrice;
    private Double busPrice;
    private Double railwayDistance;
    private Double railwayPrice;
    private Double airDistance;
    private Double airPrice;
}
