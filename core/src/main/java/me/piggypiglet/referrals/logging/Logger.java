package me.piggypiglet.referrals.logging;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface Logger {
    void info(@NotNull final String message);

    void warn(@NotNull final String message);

    void error(@NotNull final String message);

    void error(@NotNull final String message, @NotNull final Throwable throwable);

    default void info(@NotNull final String message, @NotNull final Object... variables) {
        info(String.format(message, variables));
    }

    default void warn(@NotNull final String message, @NotNull final Object... variables) {
        warn(String.format(message, variables));
    }

    default void error(@NotNull final String message, @NotNull final Object... variables) {
        error(String.format(message, variables));
    }
}
