package com.minecedia.core;

import com.hakan.core.HCore;
import com.minecedia.core.country.CountryManager;
import com.minecedia.core.database.DatabaseProvider;
import com.minecedia.core.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CediaCore extends JavaPlugin {

    private final CountryManager countryManager = new CountryManager(this);
    private final DatabaseProvider databaseProvider = new DatabaseProvider(this);
    private final UserManager userManager = new UserManager(this);

    @Override
    public void onEnable() {
        HCore.initialize(this);

        databaseProvider.initialize();
        countryManager.initialize();
        userManager.initialize();
    }

    @Override
    public void onDisable() {
        userManager.uninitialize();
    }

    public @NotNull CountryManager countryManager() {
        return this.countryManager;
    }

    public @NotNull DatabaseProvider databaseProvider() {
        return this.databaseProvider;
    }

    public @NotNull UserManager userManager() {
        return this.userManager;
    }

}