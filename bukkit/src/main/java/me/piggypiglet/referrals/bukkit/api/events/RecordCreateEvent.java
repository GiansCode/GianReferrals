package me.piggypiglet.referrals.bukkit.api.events;

import me.piggypiglet.referrals.api.ImmutableRecord;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class RecordCreateEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ImmutableRecord record;

    public RecordCreateEvent(@NotNull final ImmutableRecord record) {
        this.record = record;
    }

    @NotNull
    public ImmutableRecord record() {
        return record;
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
