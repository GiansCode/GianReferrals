package me.piggypiglet.referrals.config.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class InvalidConfigurationException extends RuntimeException {
    public InvalidConfigurationException(@NotNull final String message) {
        super(message);
    }
}
