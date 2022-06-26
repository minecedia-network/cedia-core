package com.minecedia.core.country;

import com.minecedia.core.AbstractManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CountryManager extends AbstractManager<String, Country> {

    public CountryManager(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {

        Arrays.stream(Locale.getISOCountries())
                .forEach(countryCode -> add(countryCode, new Country(countryCode, new Locale("", countryCode).getDisplayCountry())));

    }

    @Override
    public void uninitialize() {

    }


}