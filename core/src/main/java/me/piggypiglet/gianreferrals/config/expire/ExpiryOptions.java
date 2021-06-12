package me.piggypiglet.gianreferrals.config.expire;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ExpiryOptions {
    private boolean expire;
    private ExpirationPolicy policy;
    private int expiryMinutes;

    public ExpiryOptions(final boolean expire, @NotNull final ExpirationPolicy policy,
                         final int expiryMinutes) {
        this.expire = expire;
        this.policy = policy;
        this.expiryMinutes = expiryMinutes;
    }

    public boolean expire() {
        return expire;
    }

    @NotNull
    public ExpirationPolicy policy() {
        return policy;
    }

    public int expiryMinutes() {
        return expiryMinutes;
    }

    public void expire(final boolean expire) {
        this.expire = expire;
    }

    public void policy(@NotNull final ExpirationPolicy policy) {
        this.policy = policy;
    }

    public void expiryMinutes(final int expiryMinutes) {
        this.expiryMinutes = expiryMinutes;
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean expire;
        private ExpirationPolicy policy;
        private int expiryMinutes;

        private Builder() {}

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
        public Builder expiryMinutes(final int value) {
            expiryMinutes = value;
            return this;
        }

        @NotNull
        public ExpiryOptions build() {
            return new ExpiryOptions(expire, policy, expiryMinutes);
        }
    }
}
