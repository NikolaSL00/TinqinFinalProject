package com.example.domain.feignGoogleAPI.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GoogleRootElement {
    public ArrayList<String> destination_addresses;
    public ArrayList<String> origin_addresses;
    public ArrayList<Row> rows;
    public String status;
}
