package me.piggypiglet.gianreferrals.mysql.dbo.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final record ModificationRequest(@NotNull Set<Object> modified, @NotNull Set<Object> deleted) {
}
