package com.minecedia.core.user.listeners;

import com.hakan.core.HCore;
import com.minecedia.core.user.User;
import com.minecedia.core.user.UserHandler;
import com.minecedia.core.user.events.UserLoadEvent;
import com.minecedia.core.user.events.UserUnloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserConnectionListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HCore.asyncScheduler().run((() -> {
            Player player = event.getPlayer();

            User user = UserHandler.loadByUID(player.getUniqueId());
            if (user == null) {
                user = new User(player);
                user.getDatabase().insert();
            }


            User loaded = UserHandler.register(user);
            HCore.syncScheduler().run(() -> {
                loaded.getBukkit().update(player);
                Bukkit.getPluginManager().callEvent(new UserLoadEvent(loaded, player));
            });
        }));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HCore.asyncScheduler().run((() -> {
            Player player = event.getPlayer();

            User user = UserHandler.unregister(player);
            user.setLastPlayed(System.currentTimeMillis());
            user.getBukkit().loadFrom(player);
            user.getDatabase().update();

            HCore.syncScheduler().run(() -> Bukkit.getPluginManager().callEvent(new UserUnloadEvent(user, player)));
        }));
    }
}