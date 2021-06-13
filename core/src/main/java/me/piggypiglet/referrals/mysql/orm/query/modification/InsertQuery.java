package me.piggypiglet.referrals.mysql.orm.query.modification;

import me.piggypiglet.referrals.mysql.orm.structure.TableColumn;
import me.piggypiglet.referrals.mysql.orm.structure.TableStructure;
import me.piggypiglet.referrals.mysql.utils.MysqlUtils;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class InsertQuery implements ModificationQuery {
    @NotNull
    @Override
    @Language("SQL")
    public String generate(final @NotNull TableStructure table, final @NotNull Map<String, Object> data) {
        final StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO `")
                .append(table.name())
                .append("` (");

        builder.append(table.columns().stream()
                .map(TableColumn::name)
                .map(name -> '`' + name + '`')
                .collect(Collectors.joining(", ")));

        builder.append(") VALUES (");

        builder.append(table.columns().stream()
                .map(TableColumn::name)
                .map(data::get)
                .map(String::valueOf)
                .map(value -> '\'' + MysqlUtils.escapeSql(value) + '\'')
                .collect(Collectors.joining(", ")));

        builder.append(") ON DUPLICATE KEY UPDATE ");

        builder.append(table.columns().stream()
                .map(TableColumn::name)
                .map(name -> name + "='" + MysqlUtils.escapeSql(String.valueOf(data.get(name))) + '\'')
                .collect(Collectors.joining(", ")));

        builder.append(';');

        return builder.toString();
    }
}
