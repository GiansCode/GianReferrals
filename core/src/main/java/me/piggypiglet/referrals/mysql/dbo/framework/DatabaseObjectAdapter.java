package me.piggypiglet.referrals.mysql.dbo.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface DatabaseObjectAdapter<T> {
    @NotNull
    Set<T> loadFromRaw();

    @NotNull
    Object convertToRawObject(@NotNull final T object);

    @NotNull
    ModificationRequest applyToRaw(@NotNull final T object);
}
