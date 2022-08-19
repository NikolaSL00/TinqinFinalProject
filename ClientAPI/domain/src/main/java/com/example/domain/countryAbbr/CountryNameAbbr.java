package com.example.domain.countryAbbr;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountryNameAbbr {
    private final Map<String, String> countries;

    public CountryNameAbbr() {
        this.countries = new HashMap<>();
        this.init();
    }

    public String getCountryAbbreviation(String name) {
        return this.countries.get(name);
    }

    private void init() {
        this.countries.put("Albania", "AL");
        this.countries.put("Andorra", "AD");
        this.countries.put("Austria", "AT");
        this.countries.put("Armenia", "AM");
        this.countries.put("Belarus", "BY");
        this.countries.put("Belgium", "BE");
        this.countries.put("Bosnia and Herzegovina", "BA");
        this.countries.put("Bulgaria", "BG");
        this.countries.put("Croatia", "HR");
        this.countries.put("Czech Republic", "CZ");
        this.countries.put("Denmark", "DK");
        this.countries.put("Estonia", "EE");
        this.countries.put("Faroe Islands", "FO");
        this.countries.put("Finland", "FI");
        this.countries.put("France", "FR");
        this.countries.put("Germany", "DE");
        this.countries.put("Gibraltar", "GI");
        this.countries.put("Greece", "GR");
        this.countries.put("Hungary", "HU");
        this.countries.put("Iceland", "IS");
        this.countries.put("Ireland", "IE");
        this.countries.put("Italy", "IT");
        this.countries.put("Netherlands", "NL");
        this.countries.put("Norway", "NO");
        this.countries.put("Poland", "PL");
        this.countries.put("Portugal", "PT");
        this.countries.put("Romania", "RO");
        this.countries.put("Serbia", "RS");
        this.countries.put("Spain", "ES");
        this.countries.put("Sweden", "SE");
        this.countries.put("Ukraine", "UA");
        this.countries.put("United Kingdom", "GB");
    }
}
