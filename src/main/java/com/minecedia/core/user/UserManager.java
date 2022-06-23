package com.minecedia.core.user;

import com.hakan.core.HCore;
import com.minecedia.core.AbstractDataManager;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.listeners.UserConnectionListeners;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserManager extends AbstractDataManager<UUID, User> {

    public UserManager(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        UserDatabase.initialize();
        HCore.registerListeners(new UserConnectionListeners());

        HCore.asyncScheduler().after(6000).every(6000)
                .run(() -> map().values().forEach(user -> user.database().update()));
    }

    @Override
    public void uninitialize() {
        map().values().forEach(user -> user.database().update());
    }

    
    public User register(User user) {
        map().put(user.uid(), user);
        return user;
    }

    public User register(UUID uid) {
        return UserManager.register(getOrLoad(uid));
    }

    public User register(Player player) {
        return UserManager.register(player.getUniqueId());
    }

    public User unregister(User user) {
        return UserManager.unregister(user.uid());
    }

    public User unregister(Player player) {
        return UserManager.unregister(player.getUniqueId());
    }

    public User unregister(UUID uid) {
        return map().remove(uid);
    }

    public Optional<User> findByUID(UUID uid) {
        return Optional.ofNullable(map().get(uid));
    }

    public User getByUID(UUID uid) {
        return UserManager.findByUID(uid).orElseThrow(() -> new IllegalArgumentException("user couldn't found with this uid: " + uid));
    }

    public Optional<User> findByName(String name) {
        for (User user : map().values())
            if (user.name().equalsIgnoreCase(name))
                return Optional.of(user);
        return Optional.empty();
    }

    public User getByName(String name) {
        return UserManager.findByName(name).orElseThrow(() -> new IllegalArgumentException("user couldn't found with this name: " + name));
    }

    public User loadByUID(UUID uid) {
        BsonDocument query = new BsonDocument("uid", new BsonBinary(uid));
        BsonDocument document = UserDatabase.COLLECTION.find(query).first();
        return (document != null) ? new User(document) : null;
    }

    public User loadByName(String name) {
        BsonDocument query = new BsonDocument("name", new BsonString(name));
        BsonDocument document = UserDatabase.COLLECTION.find(query).first();
        return (document != null) ? new User(document) : null;
    }

    public User getOrLoad(Player player) {
        return UserManager.getOrLoad(player.getUniqueId());
    }

    public User getOrLoad(UUID uid) {
        User user1 = UserManager.findByUID(uid).orElse(null);
        if (user1 != null) return user1;

        User user2 = UserManager.loadByUID(uid);
        if (user2 != null) return user2;

        Player player = Bukkit.getPlayer(uid);
        return (player != null) ? new User(player) : null;
    }

    public User getOrLoad(String name) {
        User user1 = UserManager.findByName(name).orElse(null);
        if (user1 != null) return user1;

        User user2 = UserManager.loadByName(name);
        if (user2 != null) return user2;

        Player player = Bukkit.getPlayerExact(name);
        return (player != null) ? new User(player) : null;
    }
}