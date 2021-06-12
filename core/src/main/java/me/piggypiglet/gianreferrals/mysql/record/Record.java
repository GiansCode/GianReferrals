package me.piggypiglet.gianreferrals.mysql.record;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final record Record(@NotNull UUID uuid, @NotNull String record, @NotNull Instant expiryInstant,
                           int joins) {
}
