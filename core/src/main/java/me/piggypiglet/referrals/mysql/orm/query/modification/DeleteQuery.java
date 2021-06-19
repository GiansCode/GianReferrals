package me.piggypiglet.referrals.mysql.orm.query.modification;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import me.piggypiglet.referrals.mysql.orm.structure.TableColumn;
import me.piggypiglet.referrals.mysql.orm.structure.TableStructure;
import me.piggypiglet.referrals.mysql.utils.MysqlUtils;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class DeleteQuery {
    @NotNull
    @Language("SQL")
    public String generate(final @NotNull TableStructure table, final @NotNull Set<Map<String, Object>> data) {
        final StringBuilder builder = new StringBuilder();

        builder.append("DELETE FROM ")
                .append(table.name())
                .append(" WHERE ");

        final SetMultimap<String, String> selectors = HashMultimap.create();
        final Set<String> identifiers = table.identifiers().stream()
                .map(TableColumn::name)
                .collect(Collectors.toSet());

        data.forEach(datum -> datum.forEach((key, value) -> {
            if (identifiers.contains(key)) {
                selectors.put(key, MysqlUtils.escapeSql(String.valueOf(value)));
            }
        }));

        builder.append(selectors.asMap().entrySet().stream()
                .map(entry -> '`' + entry.getKey() + "` IN " +
                        entry.getValue().stream()
                                .map(String::valueOf)
                                .map(MysqlUtils::escapeSql)
                                .map(value -> '\'' + value + '\'')
                                .collect(Collectors.joining(", ", "(", ")")))
                .collect(Collectors.joining(" AND ")));

        builder.append(';');

        return builder.toString();
    }
}
