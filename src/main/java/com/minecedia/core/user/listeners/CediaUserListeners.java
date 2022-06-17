package com.minecedia.core.user.listeners;

import com.hakan.core.HCore;
import com.minecedia.core.user.CediaUser;
import com.minecedia.core.user.CediaUserHandler;
import com.minecedia.core.user.events.CediaUserLoadEvent;
import com.minecedia.core.user.events.CediaUserUnloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CediaUserListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HCore.asyncScheduler().run((() -> {
            CediaUser user = CediaUserHandler.register(event.getPlayer());

            HCore.syncScheduler().run(() -> {
                CediaUserLoadEvent loadEvent = new CediaUserLoadEvent(user);
                Bukkit.getPluginManager().callEvent(loadEvent);
            });
        }));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HCore.asyncScheduler().run((() -> {
            CediaUser user = CediaUserHandler.unregister(event.getPlayer());

            HCore.syncScheduler().run(() -> {
                CediaUserUnloadEvent loadEvent = new CediaUserUnloadEvent(user);
                Bukkit.getPluginManager().callEvent(loadEvent);
            });
        }));
    }
}