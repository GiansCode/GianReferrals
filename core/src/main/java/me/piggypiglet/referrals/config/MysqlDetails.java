package me.piggypiglet.referrals.config;

import me.piggypiglet.referrals.config.exceptions.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
/**
 * Please construct an instance of MysqlDetails via the MysqlDetails builder,
 * which is retrieved via {@link #builder}.
 */
public final record MysqlDetails(@NotNull String host, int port, @NotNull String username,
                                 @NotNull String password, @NotNull String database, @NotNull String tablePrefix,
                                 int poolSize) {
    /**
     * Constructs a new builder instance. Make sure to fill out every method,
     * otherwise the API will not function.
     *
     * @return Builder instance
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String host;
        private int port;
        private String username;
        private String password;
        private String database;
        private String tablePrefix;
        private int poolSize;

        private final Map<String, Supplier<Object>> fields = Map.of(
                "host", () -> host,
                "port", () -> port,
                "username", () -> username,
                "password", () -> password,
                "database", () -> database,
                "tablePrefix", () -> tablePrefix,
                "poolSize", () -> poolSize

        );

        private Builder() {}

        /**
         * Set the MySQL host. This is the same host you created your MySQL user with,
         * do not include a port in this value.
         *
         * @param value MySQL Host
         * @return Builder instance
         */
        @NotNull
        public Builder host(@NotNull final String value) {
            host = value;
            return this;
        }

        /**
         * Set the MySQL port.
         *
         * @param value MySQL port
         * @return Builder instance
         */
        @NotNull
        public Builder port(final int value) {
            port = value;
            return this;
        }

        /**
         * Set the username of your MySQL user. This is only the username, not also
         * the host. e.g. for 'user'@'127.0.0.1', we just want user.
         *
         * @param value MySQL username
         * @return Builder instance
         */
        @NotNull
        public Builder username(@NotNull final String value) {
            username = value;
            return this;
        }

        /**
         * Set the password for your MySQL user.
         *
         * @param value MySQL user password
         * @return Builder instance
         */
        @NotNull
        public Builder password(@NotNull final String value) {
            password = value;
            return this;
        }

        /**
         * Set the database name.
         *
         * @param value Database name
         * @return Builder instance
         */
        @NotNull
        public Builder database(@NotNull final String value) {
            database = value;
            return this;
        }

        /**
         * Set the table prefix. This prefix will be appended before any tables
         * the API makes in MySQL. I recommend using the platform names followed
         * by an underscore, e.g. bungee_ and bukkit_.
         *
         * @param value Table prefix
         * @return Builder instance
         */
        @NotNull
        public Builder tablePrefix(@NotNull final String value) {
            tablePrefix = value;
            return this;
        }

        /**
         * Set the hikari pool size. This is the number of active MySQL connections
         * HikariCP (and by extension, the API) will maintain. 10 is generally a good
         * number.
         *
         * @param value MySQL connection pool size
         * @return Builder instance
         */
        @NotNull
        public Builder poolSize(final int value) {
            poolSize = value;
            return this;
        }

        /**
         * Build an instance of MysqlDetails with the provided values.
         *
         * @throws InvalidConfigurationException when one or more values aren't set.
         * @return Populated MysqlDetails instance.
         */
        @NotNull
        public MysqlDetails build() {
            final String nullFields = fields.entrySet().stream()
                    .filter(entry -> entry.getValue().get() == null)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));

            if (!nullFields.isBlank()) {
                throw new InvalidConfigurationException("The following fields in MysqlDetails weren't set: " + nullFields);
            }

            return new MysqlDetails(host, port, username, password, database, tablePrefix, poolSize);
        }
    }
}
