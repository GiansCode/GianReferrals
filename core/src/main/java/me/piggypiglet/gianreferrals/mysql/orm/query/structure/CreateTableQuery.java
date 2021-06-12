package me.piggypiglet.gianreferrals.mysql.orm.query.structure;

import me.piggypiglet.gianreferrals.mysql.orm.structure.TableColumn;
import me.piggypiglet.gianreferrals.mysql.orm.structure.TableStructure;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class CreateTableQuery implements StructureQuery {
    @NotNull
    @Override
    @Language("SQL")
    public String generate(final @NotNull TableStructure structure) {
        final StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE `")
                .append(structure.name())
                .append("` (");

        structure.columns().forEach(column ->
                builder.append("`")
                        .append(column.name())
                        .append("` ")
                        .append(column.dataStructure())
                        .append(" NOT NULL, "));

        final Set<TableColumn> identifiers = structure.identifiers();

        builder.append("PRIMARY KEY (");

        builder.append(identifiers.stream()
                .map(identifier -> {
                    final String key = '`' + identifier.name() + '`';
                    final int length = identifier.length();

                    if (length != -1) {
                        return key + '(' + length + ')';
                    }

                    return key;
                }).collect(Collectors.joining(", ")));

        builder.append(")) COLLATE='utf8_general_ci' ENGINE=InnoDB;");

        return builder.toString();
    }
}
