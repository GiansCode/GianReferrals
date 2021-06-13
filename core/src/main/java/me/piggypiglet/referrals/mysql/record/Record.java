package me.piggypiglet.referrals.mysql.record;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class Record {
    private final UUID uuid;
    private String record;
    private final String cloudflareIdentifier;
    private Instant expiryInstant;
    private int joins;

    private Record(@NotNull final UUID uuid, @NotNull final String record,
                   @NotNull final String cloudflareIdentifier, @NotNull final Instant expiryInstant,
                   final int joins) {
        this.uuid = uuid;
        this.record = record;
        this.cloudflareIdentifier = cloudflareIdentifier;
        this.expiryInstant = expiryInstant;
        this.joins = joins;
    }

    @NotNull
    public UUID uuid() {
        return uuid;
    }

    @NotNull
    public String record() {
        return record;
    }

    @NotNull
    public String cloudflareIdentifier() {
        return cloudflareIdentifier;
    }

    @NotNull
    public Instant expiryInstant() {
        return expiryInstant;
    }

    public int joins() {
        return joins;
    }

    public void record(@NotNull final String record) {
        this.record = record;
    }

    public void expiryInstant(@NotNull final Instant expiryInstant) {
        this.expiryInstant = expiryInstant;
    }

    public void joins(final int joins) {
        this.joins = joins;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Record record1 = (Record) o;
        return joins == record1.joins && uuid.equals(record1.uuid) && record.equals(record1.record) && cloudflareIdentifier.equals(record1.cloudflareIdentifier) && expiryInstant.equals(record1.expiryInstant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, record, cloudflareIdentifier, expiryInstant, joins);
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID uuid;
        private String record;
        private String cloudflareIdentifier;
        private Instant expiryInstant;
        private int joins;

        private Builder() {
        }

        @NotNull
        public Builder uuid(@NotNull final UUID value) {
            uuid = value;
            return this;
        }

        @NotNull
        public Builder record(@NotNull final String value) {
            record = value;
            return this;
        }

        @NotNull
        public Builder cloudflareIdentifier(@NotNull final String value) {
            cloudflareIdentifier = value;
            return this;
        }

        @NotNull
        public Builder expiryInstant(@NotNull final Instant value) {
            expiryInstant = value;
            return this;
        }

        @NotNull
        public Builder joins(final int value) {
            joins = value;
            return this;
        }

        @NotNull
        public Record build() {
            return new Record(uuid, record, cloudflareIdentifier, expiryInstant, joins);
        }
    }
}
