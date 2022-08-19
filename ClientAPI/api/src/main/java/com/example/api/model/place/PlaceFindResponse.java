package com.example.api.model.place;

import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PlaceFindResponse implements OperationResult {
    private final String country;
    private final String city;
    private final Double latitude;
    private final Double longitude;
}
