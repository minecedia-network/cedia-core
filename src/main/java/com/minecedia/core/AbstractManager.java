package com.minecedia.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager<ID, OBJECT> {

    private final @NotNull Map<ID, OBJECT> map = new HashMap<>();
    private final @NotNull JavaPlugin plugin;

    public AbstractManager(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull Map<ID, OBJECT> map() {
        return this.map;
    }

    public @Nullable OBJECT get(ID id) {
        return this.map.getOrDefault(id, null);
    }

    public OBJECT add(ID id, OBJECT object) {
        this.map.put(id, object);
        return object;
    }

    public OBJECT remove(ID id) {
        return this.map.remove(id);
    }

    public @NotNull JavaPlugin plugin() {
        return this.plugin;
    }

    public abstract void initialize();
    public abstract void uninitialize();

}