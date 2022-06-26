package com.minecedia.core.user;

import com.hakan.core.HCore;
import com.minecedia.core.AbstractDataManager;
import com.minecedia.core.CediaCore;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.listeners.UserConnectionListeners;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class UserManager extends AbstractDataManager<UUID, User> {
    
    private final @NotNull CediaCore core;
    
    public UserManager(@NotNull CediaCore core) {
        super(core);
        this.core = core;
    }

    @Override
    public void initialize() {
        UserDatabase.initialize(core);
        HCore.registerListeners(new UserConnectionListeners(core));

        HCore.asyncScheduler()
                .after(6000)
                .every(6000)
                .run(() -> map().values().forEach(user -> user.database().update()));
    }

    @Override
    public void uninitialize() {
        map().values().forEach(user -> user.database().update());
    }

    @Override
    public User getOrLoad(UUID uuid) {
        User user1 = get(uuid);
        if (user1 != null) return user1;

        User user2 = load(uuid);
        if (user2 != null) return user2;

        Player player = Bukkit.getPlayer(uuid);
        return (player != null) ? new User(core, player) : null;
    }

    @Override
    public @Nullable User find(@NotNull String id) {
        return map().values().stream()
                .filter(user -> user.name().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable User load(@NotNull UUID uuid) {
        BsonDocument query = new BsonDocument("uid", new BsonBinary(uuid));
        BsonDocument document = UserDatabase.collection().find(query).first();

        return (document != null) ? new User(core, document) : null;
    }

    @Override
    public @Nullable User load(@NotNull String id) {
        BsonDocument query = new BsonDocument("name", new BsonString(id));
        BsonDocument document = UserDatabase.collection().find(query).first();

        return (document != null) ? new User(core, document) : null;
    }


}