package me.piggypiglet.referrals.api;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------

/**
 * Immutable representation of the mutable {@link me.piggypiglet.referrals.mysql.record.Record}
 *
 * @param uuid Player UUID
 * @param record Record name in cloudflare (e.g. piggypiglet.p1g.pw)
 * @param expiryInstant Instant when this record will expire. This may change if the joins increases,
 *                      this object will not be updated in that event (so don't store these!)
 * @param joins Current join count.
 */
public record ImmutableRecord(@NotNull UUID uuid, @NotNull String record, @NotNull Instant expiryInstant,
                              int joins) {
}
