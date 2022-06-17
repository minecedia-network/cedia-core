package com.minecedia.core.country;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class Country {

    private static final Map<String, Country> countries = new HashMap<>();

    public static void initialize() {
        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            countries.put(countryCode, new Country(countryCode, locale.getDisplayCountry()));
        }
    }

    public static Optional<Country> findByCode(String countryCode) {
        return Optional.ofNullable(countries.get(countryCode));
    }

    public static Country getByCode(String countryCode) {
        return findByCode(countryCode).orElseThrow(() -> new IllegalArgumentException("country not found with this code: " + countryCode));
    }


    private final String code;
    private final String name;

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }
}