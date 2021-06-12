package me.piggypiglet.gianreferrals.mysql.orm.query.retrieve;

import me.piggypiglet.gianreferrals.mysql.orm.structure.TableStructure;
import me.piggypiglet.gianreferrals.mysql.utils.MysqlUtils;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class SelectQuery {
    @NotNull
    @Language("SQL")
    public String generate(@NotNull final TableStructure table, @NotNull final Map<String, String> queries) {
        final StringBuilder builder = new StringBuilder();

        builder.append("SELECT * FROM ")
                .append(table.name());

        if (!queries.isEmpty()) {
            builder.append(" WHERE ");
        }

        builder.append(queries.entrySet().stream()
                .map(entry -> entry.getKey() + "='" + MysqlUtils.escapeSql(entry.getValue()) + '\'')
                .collect(Collectors.joining(" AND ")));

        builder.append(";");

        return builder.toString();
    }
}
