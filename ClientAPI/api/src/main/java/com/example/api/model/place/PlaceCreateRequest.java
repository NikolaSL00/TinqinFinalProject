package com.example.api.model.place;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceCreateRequest {
    private Double latitude;
    private Double longitude;
    private String city;
    private String country;
    private Integer numDestination;
    private Integer numDeparture;
}
