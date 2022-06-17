package com.minecedia.core.user;

import com.hakan.core.HCore;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.listeners.UserListeners;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserHandler {

    private static final Map<UUID, User> users = new HashMap<>();

    public static void initialize() {
        UserDatabase.initialize();
        HCore.registerListeners(new UserListeners());
    }

    public static void uninitialize() {
        users.values().forEach(user -> {
            user.getBukkit().load(Objects.requireNonNull(user.asPlayer()));
            user.getDatabase().update();
        });
    }


    public static Map<UUID, User> getContentSafe() {
        return new HashMap<>(users);
    }

    public static Map<UUID, User> getContent() {
        return users;
    }

    public static Collection<User> getValuesSafe() {
        return new ArrayList<>(users.values());
    }

    public static Collection<User> getValues() {
        return users.values();
    }

    public static User register(User user) {
        users.put(user.getUID(), user);
        return user;
    }

    public static User register(UUID uid) {
        return UserHandler.register(getOrLoad(uid));
    }

    public static User register(Player player) {
        return UserHandler.register(player.getUniqueId());
    }

    public static User unregister(User user) {
        return UserHandler.unregister(user.getUID());
    }

    public static User unregister(Player player) {
        return UserHandler.unregister(player.getUniqueId());
    }

    public static User unregister(UUID uid) {
        return users.remove(uid);
    }

    public static Optional<User> findByUID(UUID uid) {
        return Optional.ofNullable(users.get(uid));
    }

    public static User getByUID(UUID uid) {
        return UserHandler.findByUID(uid).orElseThrow(() -> new IllegalArgumentException("user couldn't found with this uid: " + uid));
    }

    public static Optional<User> findByName(String name) {
        for (User user : users.values())
            if (user.getName().equalsIgnoreCase(name))
                return Optional.of(user);
        return Optional.empty();
    }

    public static User getByName(String name) {
        return UserHandler.findByName(name).orElseThrow(() -> new IllegalArgumentException("user couldn't found with this name: " + name));
    }

    public static User loadByUID(UUID uid) {
        BsonDocument query = new BsonDocument("uid", new BsonBinary(uid));
        BsonDocument document = UserDatabase.COLLECTION.find(query).first();
        return (document != null) ? new User(document) : null;
    }

    public static User loadByName(String name) {
        BsonDocument query = new BsonDocument("name", new BsonString(name));
        BsonDocument document = UserDatabase.COLLECTION.find(query).first();
        return (document != null) ? new User(document) : null;
    }

    public static User getOrLoad(Player player) {
        return UserHandler.getOrLoad(player.getUniqueId());
    }

    public static User getOrLoad(UUID uid) {
        User user1 = UserHandler.findByUID(uid).orElse(null);
        if (user1 != null) return user1;

        User user2 = UserHandler.loadByUID(uid);
        if (user2 != null) return user2;

        Player player = Bukkit.getPlayer(uid);
        return (player != null) ? new User(player) : null;
    }

    public static User getOrLoad(String name) {
        User user1 = UserHandler.findByName(name).orElse(null);
        if (user1 != null) return user1;

        User user2 = UserHandler.loadByName(name);
        if (user2 != null) return user2;

        Player player = Bukkit.getPlayerExact(name);
        return (player != null) ? new User(player) : null;
    }
}