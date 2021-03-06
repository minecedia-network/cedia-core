package com.minecedia.core.user.listeners;

import com.hakan.core.HCore;
import com.minecedia.core.CediaCore;
import com.minecedia.core.user.User;
import com.minecedia.core.user.events.UserLoadEvent;
import com.minecedia.core.user.events.UserUnloadEvent;
import com.minecedia.core.user.skin.UserSkinData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class UserConnectionListeners implements Listener {

    private final @NotNull CediaCore core;

    public UserConnectionListeners(@NotNull CediaCore core) {
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HCore.asyncScheduler().run((() -> {

            Player player = event.getPlayer();
            User user = core.userManager().load(player.getUniqueId());

            if (user == null) {
                user = new User(core, player);
                user.database().insert();
            }

            User loaded = core.userManager().add(user.uid(), user);
            HCore.syncScheduler().run(() -> Bukkit.getPluginManager().callEvent(new UserLoadEvent(loaded, player)));
        }));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HCore.asyncScheduler().run((() -> {
            Player player = event.getPlayer();

            User user = core.userManager().remove(player.getUniqueId());
            UserSkinData skinData = user.skin();

            String[] textures = UserSkinData.loadSkinData(user);
            skinData.setTexture(textures[0]);
            skinData.setSignature(textures[1]);
            skinData.setShortTexture(textures[2]);

            user.setLastPlayed(System.currentTimeMillis());
            user.database().update();

            HCore.syncScheduler().run(() -> Bukkit.getPluginManager().callEvent(new UserUnloadEvent(user, player)));
        }));
    }

}