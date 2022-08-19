package com.example.domain.feignLocatePlaceAPI.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Place {
    private Double latitude;
    private Double longitude;
    private String name;
    private String city;
    private String country;
}
