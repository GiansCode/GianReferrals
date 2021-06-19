package me.piggypiglet.referrals.bungee.api.events;

import me.piggypiglet.referrals.api.ImmutableRecord;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ReferralRemovalEvent extends Event {
    private final ImmutableRecord old;
    private final ImmutableRecord current;
    private final UUID joiner;

    public ReferralRemovalEvent(@NotNull final ImmutableRecord old, @NotNull final ImmutableRecord current,
                                @Nullable final UUID joiner) {
        this.old = old;
        this.current = current;
        this.joiner = joiner;
    }

    @NotNull
    public ImmutableRecord old() {
        return old;
    }

    @NotNull
    public ImmutableRecord current() {
        return current;
    }

    @Nullable
    public UUID joiner() {
        return joiner;
    }
}
