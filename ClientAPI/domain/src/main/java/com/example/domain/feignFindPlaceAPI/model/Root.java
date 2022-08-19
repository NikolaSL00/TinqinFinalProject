package com.example.domain.feignFindPlaceAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Root {
    public String id;
    public int geonameId;
    public String type;
    public String name;
    public int population;
    public int elevation;
    public String timezoneId;
    public Country country;
    public AdminDivision1 adminDivision1;
    public AdminDivision2 adminDivision2;
    public double score;
    public Coordinates coordinates;
}
