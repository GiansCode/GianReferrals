package me.piggypiglet.referrals.bukkit.api.events;

import me.piggypiglet.referrals.api.ImmutableRecord;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ReferralAddendumEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ImmutableRecord old;
    private final ImmutableRecord current;

    public ReferralAddendumEvent(@NotNull final ImmutableRecord old, @NotNull final ImmutableRecord current) {
        this.old = old;
        this.current = current;
    }

    @NotNull
    public ImmutableRecord old() {
        return old;
    }

    @NotNull
    public ImmutableRecord current() {
        return current;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
