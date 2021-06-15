package me.piggypiglet.referrals.bootstrap.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BootstrapHaltException extends RuntimeException {
    public BootstrapHaltException(@NotNull final String message) {
        super(message);
    }
}
