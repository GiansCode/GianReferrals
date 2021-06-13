package me.piggypiglet.referrals.config.expire;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public record ExpiryOptions(boolean expire, ExpirationPolicy policy, long expiryCheckPeriodMinutes,
                            long expiryMinutes) {
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean expire;
        private ExpirationPolicy policy;
        private long expiryCheckPeriodMinutes;
        private long expiryMinutes;

        private Builder() { }

        @NotNull
        public Builder expire(final boolean value) {
            expire = value;
            return this;
        }

        @NotNull
        public Builder policy(@NotNull final ExpirationPolicy value) {
            policy = value;
            return this;
        }

        @NotNull
        public Builder expiryCheckPeriodMinutes(final long value) {
            expiryCheckPeriodMinutes = value;
            return this;
        }

        @NotNull
        public Builder expiryMinutes(final long value) {
            expiryMinutes = value;
            return this;
        }

        @NotNull
        public ExpiryOptions build() {
            return new ExpiryOptions(expire, policy, expiryCheckPeriodMinutes, expiryMinutes);
        }
    }
}
