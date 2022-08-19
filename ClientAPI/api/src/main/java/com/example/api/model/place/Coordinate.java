package com.example.api.model.place;

import com.example.api.base.OperationInput;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate implements OperationInput {
    private Double latitude;
    private Double longitude;
    private String city;
    private String country;
    @Override
    public String toString() {
        return latitude.toString() + "," + longitude.toString();
    }
}
