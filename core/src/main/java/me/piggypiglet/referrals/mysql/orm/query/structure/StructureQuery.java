package me.piggypiglet.referrals.mysql.orm.query.structure;

import me.piggypiglet.referrals.mysql.orm.structure.TableStructure;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface StructureQuery {
    @NotNull
    @Language("SQL")
    String generate(@NotNull final TableStructure structure);
}
