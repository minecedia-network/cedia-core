package com.minecedia.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDataManager<ID, OBJECT> extends AbstractManager<ID, OBJECT> {


    public AbstractDataManager(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    public @Nullable OBJECT getOrLoad(ID id) {
        return get(id, true);
    }

    public @Nullable OBJECT get(ID id, boolean load) {
        OBJECT obj = get(id);
        return obj == null && load ? load(id) : obj;
    }

    public @Nullable OBJECT findOrLoad(@NotNull String id) {
        OBJECT obj = find(id);
        return obj == null ? load(id) : obj;
    }

    public abstract @Nullable OBJECT find(@NotNull String id);

    public abstract @Nullable OBJECT load(@NotNull ID id);
    public abstract @Nullable OBJECT load(@NotNull String id);

}
