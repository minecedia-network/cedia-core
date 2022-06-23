package com.minecedia.core.country;

import org.jetbrains.annotations.NotNull;

public class Country {

    private final @NotNull String code;
    private final @NotNull String name;

    public Country(@NotNull String code, @NotNull String name) {
        this.code = code;
        this.name = name;
    }

    public @NotNull String name() {
        return this.name;
    }

    public @NotNull String code() {
        return this.code;
    }

}