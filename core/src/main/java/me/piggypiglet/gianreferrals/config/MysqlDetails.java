package me.piggypiglet.gianreferrals.config;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final record MysqlDetails(@NotNull String host, int port, @NotNull String username,
                                 @NotNull String password, @NotNull String database, @NotNull String tablePrefix,
                                 int poolSize) {
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

        private Builder() {}

        @NotNull
        public Builder host(@NotNull final String value) {
            host = value;
            return this;
        }

        @NotNull
        public Builder port(final int value) {
            port = value;
            return this;
        }

        @NotNull
        public Builder username(@NotNull final String value) {
            username = value;
            return this;
        }

        @NotNull
        public Builder password(@NotNull final String value) {
            password = value;
            return this;
        }

        @NotNull
        public Builder database(@NotNull final String value) {
            database = value;
            return this;
        }

        @NotNull
        public Builder tablePrefix(@NotNull final String value) {
            tablePrefix = value;
            return this;
        }

        @NotNull
        public Builder poolSize(final int value) {
            poolSize = value;
            return this;
        }

        @NotNull
        public MysqlDetails build() {
            return new MysqlDetails(host, port, username, password, database, tablePrefix, poolSize);
        }
    }
}
