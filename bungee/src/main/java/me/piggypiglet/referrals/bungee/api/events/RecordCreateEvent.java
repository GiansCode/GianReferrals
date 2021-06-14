package me.piggypiglet.referrals.bungee.api.events;

import me.piggypiglet.referrals.api.ImmutableRecord;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class RecordCreateEvent extends Event {
    private final ImmutableRecord record;

    public RecordCreateEvent(@NotNull final ImmutableRecord record) {
        this.record = record;
    }

    @NotNull
    public ImmutableRecord record() {
        return record;
    }
}
