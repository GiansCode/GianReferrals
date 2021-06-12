package me.piggypiglet.gianreferrals.config;

import me.piggypiglet.gianreferrals.config.expire.ExpiryOptions;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final record Config(@NotNull String apiKey, @NotNull String ip, @NotNull String domain,
                           @NotNull String zone, @NotNull ExpiryOptions expiry, @NotNull MysqlDetails mysql) {
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String apiKey;
        private String ip;
        private String domain;
        private String zone;
        private ExpiryOptions expiry;
        private MysqlDetails mysql;

        private Builder() {}

        @NotNull
        public Builder apiKey(@NotNull final String value) {
            apiKey = value;
            return this;
        }

        @NotNull
        public Builder ip(@NotNull final String value) {
            ip = value;
            return this;
        }

        @NotNull
        public Builder domain(@NotNull final String value) {
            domain = value;
            return this;
        }

        @NotNull
        public Builder zone(@NotNull final String value) {
            zone = value;
            return this;
        }

        @NotNull
        public Builder expiry(@NotNull final ExpiryOptions value) {
            expiry = value;
            return this;
        }

        @NotNull
        public Builder mysql(@NotNull final MysqlDetails value) {
            mysql = value;
            return this;
        }

        public Config build() {
            return new Config(apiKey, ip, domain, zone, expiry, mysql);
        }
    }
}
