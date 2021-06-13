package me.piggypiglet.referrals.mysql.orm.query;

import co.aikar.idb.Database;
import com.google.inject.Inject;
import me.piggypiglet.referrals.logging.Logger;
import me.piggypiglet.referrals.mysql.orm.query.modification.DeleteQuery;
import me.piggypiglet.referrals.mysql.orm.query.modification.InsertQuery;
import me.piggypiglet.referrals.mysql.orm.query.retrieve.SelectQuery;
import me.piggypiglet.referrals.mysql.orm.query.structure.CreateTableQuery;
import me.piggypiglet.referrals.mysql.orm.query.structure.ExistsQuery;
import me.piggypiglet.referrals.mysql.orm.structure.TableStructure;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class QueryRunner {
    private final Database database;

    private final ExistsQuery exists;
    private final CreateTableQuery schema;
    private final InsertQuery insert;
    private final DeleteQuery delete;
    private final SelectQuery select;

    private final Logger logger;

    @Inject
    public QueryRunner(@NotNull final Database database, @NotNull final ExistsQuery exists,
                       @NotNull final CreateTableQuery schema, @NotNull final InsertQuery insert,
                       @NotNull final DeleteQuery delete, @NotNull final SelectQuery select,
                       @NotNull final Logger logger) {
        this.database = database;

        this.exists = exists;
        this.schema = schema;
        this.insert = insert;
        this.delete = delete;
        this.select = select;

        this.logger = logger;
    }

    public boolean exists(@NotNull final TableStructure structure) {
        @Language("SQL") final String query = exists.generate(structure);
        final String error = "Something went wrong when checking if " + structure.name() + " exists with:\n" + query;

        return tryAndCatch(() -> !database.getResults(query).isEmpty(), error)
                .orElse(false);
    }

    public void applySchema(@NotNull final TableStructure table) {
        if (exists(table)) {
            logger.info("%s already exists, not applying schematic.", table.name());
            return;
        }

        @Language("SQL") final String query = schema.generate(table);
        final String error = "Something went wrong when applying " + table.name() + "'s schema:\n" + query;

        tryAndCatch(() -> database.executeUpdate(query), error)
                .ifPresent(i -> logger.info("Successfully applied schematic for %s.", table.name()));
    }

    public void insert(@NotNull final TableStructure table, @NotNull final Map<String, Object> data) {
        @Language("SQL") final String query = insert.generate(table, data);
        final String error = "Something went wrong when inserting into " + table.name() + " with the following query:\n" + query;

        tryAndCatch(() -> database.executeUpdate(query), error);
    }

    public void delete(@NotNull final TableStructure table, @NotNull final Map<String, Object> queries) {
        @Language("SQL") final String query = delete.generate(table, queries);
        final String error = "Something went wrong when deleting from " + table.name() + " with the following query:\n" + query;

        tryAndCatch(() -> database.executeUpdate(query), error);
    }

    @NotNull
    public Set<Map<String, Object>> load(@NotNull final TableStructure table, @NotNull final Map<String, String> queries) {
        @Language("SQL") final String query = select.generate(table, queries);
        final String error = "Something went wrong when loading from " + table.name() + " with the following query:\n" + query;

        return tryAndCatch(() -> database.getResults(query), error)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @NotNull
    private <T> Optional<T> tryAndCatch(@NotNull final ThrowingSupplier<T> function, @NotNull final String message) {
        try {
            return Optional.of(function.get());
        } catch (SQLException exception) {
            logger.error(message, exception);
        }

        return Optional.empty();
    }

    @FunctionalInterface
    private interface ThrowingSupplier<T> {
        @NotNull
        T get() throws SQLException;
    }
}
