package com.minecedia.core.user.events;

import com.minecedia.core.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class UserLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }


    private final User user;

    public UserLoadEvent(@Nonnull User user) {
        this.user = user;
    }

    @Nonnull
    public HandlerList getHandlers() {
        return handlers;
    }

    public User getUser() {
        return this.user;
    }
}