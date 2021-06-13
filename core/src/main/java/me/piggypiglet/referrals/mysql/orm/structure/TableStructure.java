package me.piggypiglet.referrals.mysql.orm.structure;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public record TableStructure(@NotNull Class<?> clazz, @NotNull String name, @NotNull Set<TableColumn> identifiers,
                             @NotNull Set<TableColumn> columns) {
}
