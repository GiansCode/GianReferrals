package me.piggypiglet.gianreferrals.guice.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class InjectionException extends RuntimeException {
    public InjectionException(@NotNull final Exception exception) {
        super(exception);
    }
}
