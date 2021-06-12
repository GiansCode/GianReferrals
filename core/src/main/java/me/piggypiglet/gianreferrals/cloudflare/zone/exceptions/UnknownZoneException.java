package me.piggypiglet.gianreferrals.cloudflare.zone.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class UnknownZoneException extends RuntimeException {
    public UnknownZoneException(@NotNull final String message) {
        super(message);
    }
}
