package com.minecedia.core.user.events;

import com.minecedia.core.user.CediaUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class CediaUserUnloadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }


    private final CediaUser user;

    public CediaUserUnloadEvent(@Nonnull CediaUser user) {
        this.user = user;
    }

    @Nonnull
    public HandlerList getHandlers() {
        return handlers;
    }

    public CediaUser getUser() {
        return this.user;
    }
}