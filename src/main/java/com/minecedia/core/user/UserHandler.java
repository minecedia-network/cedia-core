package com.minecedia.core.user;

import com.hakan.core.HCore;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.listeners.UserListeners;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserHandler {

    private static final Map<UUID, User> users = new HashMap<>();

    public static void initialize() {
        UserDatabase.initialize();
        HCore.registerListeners(new UserListeners());
    }

    public static void uninitialize() {
        for (User user : users.values()) {
            user.getBukkit().loadFromPlayer();
            user.getDatabase().update();
        }
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
        return users.put(user.getUID(), user);
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

    public static User load(UUID uid) {
        BsonDocument query = new BsonDocument("uid", new BsonBinary(uid));
        BsonDocument document = UserDatabase.COLLECTION.find(query).first();
        return (document != null) ? new User(document) : null;
    }

    public static User getOrLoad(Player player) {
        return UserHandler.getOrLoad(player.getUniqueId());
    }

    public static User getOrLoad(UUID uid) {
        User user1 = users.get(uid);
        if (user1 != null) return user1;

        User user2 = UserHandler.load(uid);
        if (user2 != null) return user2;

        Player player = Bukkit.getPlayer(uid);
        return (player != null) ? new User(player) : null;
    }
}