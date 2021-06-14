package me.piggypiglet.referrals.bungee.api.events;

import me.piggypiglet.referrals.api.ImmutableRecord;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ReferralAddendumEvent extends Event {
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
}
