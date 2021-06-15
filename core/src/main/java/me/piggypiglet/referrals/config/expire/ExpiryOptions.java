package me.piggypiglet.referrals.config.expire;

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
 * Please construct an instance of ExpiryOptions via the ExpiryOptions builder,
 * which is retrieved via {@link #builder}.
 */
public record ExpiryOptions(boolean expire, ExpirationPolicy policy, long expiryCheckPeriodMinutes,
                            long expiryMinutes) {
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
        private boolean expire;
        private ExpirationPolicy policy;
        private long expiryCheckPeriodMinutes;
        private long expiryMinutes;

        private final Map<String, Supplier<Object>> fields = Map.of(
                "expire", () -> expire,
                "policy", () -> policy,
                "expiryCheckPeriodMinutes", () -> expiryCheckPeriodMinutes,
                "expiryMinutes", () -> expiryMinutes
        );

        private Builder() { }

        /**
         * Set whether to actually expire records or not.
         *
         * @param value Expire, yay or nay
         * @return Builder instance
         */
        @NotNull
        public Builder expire(final boolean value) {
            expire = value;
            return this;
        }

        /**
         * Set the type of expiration to use. Either expire based on the initial timestamp,
         * or expire after the last time the record was accessed. See the docs on
         * {@link ExpirationPolicy} for more info.
         *
         * @param value ExpirationPolicy
         * @return Builder instance
         */
        @NotNull
        public Builder policy(@NotNull final ExpirationPolicy value) {
            policy = value;
            return this;
        }

        /**
         * How frequently (in minutes) to compare record expiration timestamps to the current
         * timestamp, to see whether they should be deleted or not.
         *
         * @param value Checking period in minutes
         * @return Builder instance
         */
        @NotNull
        public Builder expiryCheckPeriodMinutes(final long value) {
            expiryCheckPeriodMinutes = value;
            return this;
        }

        /**
         * How long it should take for a record to expire (depending on the expiration policy) in
         * minutes.
         *
         * @param value Expiration time in minutes
         * @return Builder instance
         */
        @NotNull
        public Builder expiryMinutes(final long value) {
            expiryMinutes = value;
            return this;
        }

        /**
         * Build an instance of ExpiryOptions with the provided values.
         *
         * @throws InvalidConfigurationException when one or more values aren't set.
         * @return Populated ExpiryOptions instance
         */
        @NotNull
        public ExpiryOptions build() {
            final String nullFields = fields.entrySet().stream()
                    .filter(entry -> entry.getValue().get() == null)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));

            if (!nullFields.isBlank()) {
                throw new InvalidConfigurationException("The following fields in ExpiryOptions weren't set: " + nullFields);
            }

            return new ExpiryOptions(expire, policy, expiryCheckPeriodMinutes, expiryMinutes);
        }
    }
}
