package me.piggypiglet.referrals.api.implementation.platform;

import me.piggypiglet.referrals.api.ImmutableRecord;
import me.piggypiglet.referrals.mysql.record.Record;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface PlatformReferrals {
    void fire(@NotNull final PlatformEvent event, @NotNull final ImmutableRecord old,
              @NotNull final ImmutableRecord current);
}
