package com.example.api.model.place;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceLocateRequest {
    private Double latitude;
    private Double longitude;
}
