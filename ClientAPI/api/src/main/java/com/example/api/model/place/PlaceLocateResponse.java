package com.example.api.model.place;

import com.example.api.base.OperationResult;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceLocateResponse implements OperationResult {
    private String name;
    private String city;
    private String country;
}
