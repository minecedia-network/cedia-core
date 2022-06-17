package com.minecedia.core;

import com.hakan.core.HCore;
import com.minecedia.core.country.Country;
import com.minecedia.core.database.DatabaseProvider;
import com.minecedia.core.user.UserHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CediaCore extends JavaPlugin {

    @Override
    public void onEnable() {
        HCore.initialize(this);
        Country.initialize();
        DatabaseProvider.initialize();
        UserHandler.initialize();
    }

    @Override
    public void onDisable() {
        UserHandler.uninitialize();
    }
}