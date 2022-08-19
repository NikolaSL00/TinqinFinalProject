package com.example.core.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class DoubleRounder {

    public Double roundDouble(Double number){
        return (double)Math.round(number * 1000d) / 1000d;
    }

}
