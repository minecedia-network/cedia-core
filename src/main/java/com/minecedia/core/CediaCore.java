package com.minecedia.core;

import com.minecedia.core.country.Country;
import com.minecedia.core.database.CediaDatabase;
import com.minecedia.core.user.CediaUserHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CediaCore extends JavaPlugin {

    @Override
    public void onEnable() {
        Country.initialize();
        CediaDatabase.initialize();
        CediaUserHandler.initialize();
    }

    @Override
    public void onDisable() {

    }
}