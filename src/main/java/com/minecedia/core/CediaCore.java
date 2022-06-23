package com.minecedia.core;

import com.hakan.core.HCore;
import com.minecedia.core.country.CountryManager;
import com.minecedia.core.database.DatabaseProvider;
import com.minecedia.core.user.UserHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CediaCore extends JavaPlugin {

    private final CountryManager countryManager = new CountryManager(this);

    @Override
    public void onEnable() {
        HCore.initialize(this);
        DatabaseProvider.initialize(this);
        countryManager.initialize();
        UserHandler.initialize();
    }

    @Override
    public void onDisable() {
        UserHandler.uninitialize();
    }

    public @NotNull CountryManager countryManager() {
        return this.countryManager;
    }

}