package com.example.api.model.place;

import com.example.api.base.OperationInput;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceLocateRequest implements OperationInput {
    private Double latitude;
    private Double longitude;

    @Override
    public String toString() {
        return latitude.toString() + "," + longitude.toString();
    }
}
