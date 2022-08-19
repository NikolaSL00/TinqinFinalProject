package com.example.api.model.place;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlaceDTO {
    private String city;
    private String county;
}
