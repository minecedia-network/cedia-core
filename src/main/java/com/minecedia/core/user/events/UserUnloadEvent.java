package com.minecedia.core.user.events;

import com.minecedia.core.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class UserUnloadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }


    private final User user;
    private final Player player;

    public UserUnloadEvent(User user, Player player) {
        this.user = user;
        this.player = player;
    }

    @Nonnull
    public HandlerList getHandlers() {
        return handlers;
    }

    public User getUser() {
        return this.user;
    }

    public Player getPlayer() {
        return this.player;
    }
}