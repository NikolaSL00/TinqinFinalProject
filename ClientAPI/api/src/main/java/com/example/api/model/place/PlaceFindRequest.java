package com.example.api.model.place;

import com.example.api.base.OperationInput;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceFindRequest implements OperationInput {
    private final String country;
    private final String city;
}
