package me.piggypiglet.referrals.config;

import me.piggypiglet.referrals.config.exceptions.InvalidConfigurationException;
import me.piggypiglet.referrals.config.expire.ExpiryOptions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------

/**
 * Please construct an instance of Config via the config builder,
 * which is retrieved via {@link #builder}.
 */
public final record Config(@NotNull String apiKey, @NotNull String ip, @NotNull String domain,
                           @NotNull String zone, @NotNull ExpiryOptions expiry, @NotNull MysqlDetails mysql) {
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
        private String apiKey;
        private String ip;
        private String domain;
        private String zone;
        private ExpiryOptions expiry;
        private MysqlDetails mysql;

        private final Map<String, Supplier<Object>> fields = Map.of(
                "apiKey", () -> apiKey,
                "ip", () -> ip,
                "domain", () -> domain,
                "zone", () -> zone,
                "expiry", () -> expiry,
                "mysql", () -> mysql
        );

        private Builder() {
        }

        /**
         * Sets the Cloudflare API key. An API key can be created <a href="https://dash.cloudflare.com/profile/api-tokens">here</a>.
         * <ol>
         *     <li>Click the "Create Token" button, and use the "Edit zone DNS" template.</li>
         *     <li>Under zone resources, select the domain you're using in the third dropdown.</li>
         *     <li>Click continue to summary.</li>
         *     <li>Click "Create Token".</li>
         *     <li>Click the copy button next to the token. Save this somewhere safe, as you will not
         *     be able to access this token again through cloudflare. Additionally run the given command
         *     to test if the token is working.</li>
         * </ol>
         *
         * @param value Cloudflare API key/token
         * @return Builder instance
         */
        @NotNull
        public Builder apiKey(@NotNull final String value) {
            apiKey = value;
            return this;
        }

        /**
         * Set the numerical server IP. This is used when creating the records in cloudflare,
         * additionally there's a check on login to ignore connections which use this
         * ip directly.
         *
         * @param value Numerical IP (e.g. 127.0.0.1)
         * @return Builder instance
         */
        @NotNull
        public Builder ip(@NotNull final String value) {
            ip = value;
            return this;
        }

        /**
         * Set the domain that usernames will be appended to. This doesn't necessarily equal
         * the zone, e.g. if your minecraft domain is "mc.server.com" but your domain is "server.com",
         * you'd put "mc.server.com" here and "server.com" as the zone.
         *
         * @param value Server domain
         * @return Builder instance
         */
        @NotNull
        public Builder domain(@NotNull final String value) {
            domain = value.toLowerCase();
            return this;
        }

        /**
         * Set the zone name in cloudflare to look for. This is equal to your base domain,
         * with no records appended infront. e.g. server.com. See {@link #domain(String)} javadoc for further
         * explanation.
         *
         * @param value Cloudflare zone
         * @return Builder instance
         */
        @NotNull
        public Builder zone(@NotNull final String value) {
            zone = value;
            return this;
        }

        /**
         * Set the expiry options for the referrals API. i.e. whether to actually expire records,
         * how to expire records, how long it'll take to expire records, and how frequently to check
         * for expired records. Use {@link ExpiryOptions#builder} to construct an instance of {@link ExpiryOptions}.
         *
         * @param value {@link ExpiryOptions} instance
         * @return Builder instance
         */
        @NotNull
        public Builder expiry(@NotNull final ExpiryOptions value) {
            expiry = value;
            return this;
        }

        /**
         * Set the MySQL options for the referrals API. i.e. the host, port, username, password,
         * database, table prefix, and pool size. Use {@link MysqlDetails#builder} to construct an
         * instance of {@link MysqlDetails}.
         *
         * @param value {@link MysqlDetails} instance
         * @return Builder instance
         */
        @NotNull
        public Builder mysql(@NotNull final MysqlDetails value) {
            mysql = value;
            return this;
        }

        /**
         * Build an instance of Config with the provided values.
         *
         * @throws InvalidConfigurationException when one or more values aren't set.
         * @return Populated Config instance
         */
        public Config build() {
            final String nullFields = fields.entrySet().stream()
                    .filter(entry -> entry.getValue().get() == null)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));

            if (!nullFields.isBlank()) {
                throw new InvalidConfigurationException("The following fields in Config weren't set: " + nullFields);
            }

            return new Config(apiKey, ip, domain, zone, expiry, mysql);
        }
    }
}
