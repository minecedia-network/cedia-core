package com.minecedia.core.user;

import com.hakan.core.HCore;
import com.minecedia.core.user.listeners.CediaUserListeners;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CediaUserHandler {

    private static final Map<UUID, CediaUser> users = new HashMap<>();

    public static void initialize() {
        HCore.registerListeners(new CediaUserListeners());
    }

    public static Map<UUID, CediaUser> getContentSafe() {
        return new HashMap<>(users);
    }

    public static Map<UUID, CediaUser> getContent() {
        return users;
    }

    public static Collection<CediaUser> getValuesSafe() {
        return new ArrayList<>(users.values());
    }

    public static Collection<CediaUser> getValues() {
        return users.values();
    }

    public static CediaUser register(CediaUser user) {
        return users.put(user.getUID(), user);
    }

    public static CediaUser register(UUID uid) {
        return CediaUserHandler.register(getOrLoad(uid));
    }

    public static CediaUser register(Player player) {
        return CediaUserHandler.register(player.getUniqueId());
    }

    public static CediaUser unregister(CediaUser user) {
        return CediaUserHandler.unregister(user.getUID());
    }

    public static CediaUser unregister(Player player) {
        return CediaUserHandler.unregister(player.getUniqueId());
    }

    public static CediaUser unregister(UUID uid) {
        return users.remove(uid);
    }

    public static Optional<CediaUser> findByUID(UUID uid) {
        return Optional.ofNullable(users.get(uid));
    }

    public static CediaUser getByUID(UUID uid) {
        return CediaUserHandler.findByUID(uid).orElseThrow(() -> new IllegalArgumentException("user couldn't found with this uid: " + uid));
    }

    public static CediaUser getOrLoad(Player player) {
        return CediaUserHandler.getOrLoad(player.getUniqueId());
    }

    public static CediaUser getOrLoad(UUID uid) {
        //TODO: load user from database or create new
        return new CediaUser(null);
    }
}