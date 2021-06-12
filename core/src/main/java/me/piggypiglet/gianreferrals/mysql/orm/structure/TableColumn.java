package me.piggypiglet.gianreferrals.mysql.orm.structure;

import me.piggypiglet.gianreferrals.mysql.orm.structure.objects.SqlDataStructures;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public record TableColumn(@NotNull String name, @NotNull SqlDataStructures dataStructure, int length) {
}
