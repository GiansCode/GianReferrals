package me.piggypiglet.gianreferrals.mysql.registerables;

import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import com.google.inject.Inject;
import me.piggypiglet.gianreferrals.bootstrap.framework.Registerable;
import me.piggypiglet.gianreferrals.config.Config;
import me.piggypiglet.gianreferrals.config.MysqlDetails;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class MysqlRegisterable extends Registerable {
    private final MysqlDetails details;

    @Inject
    public MysqlRegisterable(@NotNull final Config config) {
        this.details = config.mysql();
    }

    @Override
    public void execute() {
        final DatabaseOptions options = DatabaseOptions.builder()
                .mysql(details.username(), details.password(), details.database(), details.host() + ':' + details.port())
                .dataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource")
                .build();
        final Database database = PooledDatabaseOptions.builder()
                .options(options)
                .maxConnections(details.poolSize())
                .createHikariDatabase();
        addBinding(Database.class, database);
    }
}
