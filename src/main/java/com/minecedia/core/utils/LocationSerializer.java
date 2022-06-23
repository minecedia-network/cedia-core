package com.minecedia.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LocationSerializer {

    public static @NotNull String serialize(Location location) {
        Objects.requireNonNull(location, "Location cannot be null!");
        Objects.requireNonNull(location.getWorld(), "World cannot be null!");

        return
                location.getWorld().getName() + ":"
                + location.getBlockX() + ":"
                + location.getBlockY() + ":"
                + location.getBlockZ() + ":"
                + location.getYaw() + ":"
                + location.getPitch();
    }

    public static @NotNull Location deserialize(String stringLoc) {
        Objects.requireNonNull(stringLoc, "Location(as string) cannot be null!");

        String[] splitLoc = stringLoc.split(":");
        return new Location(
                Bukkit.getWorld(splitLoc[0]),
                Double.parseDouble(splitLoc[1]),
                Double.parseDouble(splitLoc[2]),
                Double.parseDouble(splitLoc[3]),
                Float.parseFloat(splitLoc[4]),
                Float.parseFloat(splitLoc[5])
        );
    }

}