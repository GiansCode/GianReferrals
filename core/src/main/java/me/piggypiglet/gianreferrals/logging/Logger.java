package me.piggypiglet.gianreferrals.logging;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public abstract class Logger {
    public abstract void info(@NotNull final String message);

    public abstract void warn(@NotNull final String message);

    public abstract void error(@NotNull final String message);

    public abstract void error(@NotNull final String message, @NotNull final Throwable throwable);

    public void info(@NotNull final String message, @NotNull final Object... variables) {
        info(String.format(message, variables));
    }

    public void warn(@NotNull final String message, @NotNull final Object... variables) {
        warn(String.format(message, variables));
    }

    public void error(@NotNull final String message, @NotNull final Object... variables) {
        error(String.format(message, variables));
    }
}
