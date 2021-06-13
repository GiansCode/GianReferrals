package me.piggypiglet.referrals.mysql.tables;

import me.piggypiglet.referrals.mysql.orm.annotations.Identifier;
import me.piggypiglet.referrals.mysql.orm.annotations.Length;
import me.piggypiglet.referrals.mysql.orm.annotations.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@Table("records")
public final class RawRecord {
    @Identifier @Length(36)
    private final String uuid;
    private final String record;
    private final String cloudflareIdentifier;
    private final String expiryInstant;
    private final int joins;

    public RawRecord(@NotNull final String uuid, @NotNull final String record,
                     @NotNull final String cloudflareIdentifier, @NotNull final String expiryInstant,
                     final int joins) {
        this.uuid = uuid;
        this.record = record;
        this.cloudflareIdentifier = cloudflareIdentifier;
        this.expiryInstant = expiryInstant;
        this.joins = joins;
    }

    @NotNull
    public String uuid() {
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
    public String expiryInstant() {
        return expiryInstant;
    }

    public int joins() {
        return joins;
    }

    public boolean actualEquals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final RawRecord rawRecord = (RawRecord) object;

        return uuid.equals(rawRecord.uuid)
                && record.equals(rawRecord.record)
                && cloudflareIdentifier.equals(rawRecord.cloudflareIdentifier)
                && expiryInstant.equals(rawRecord.expiryInstant)
                && joins == rawRecord.joins;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final RawRecord rawRecord = (RawRecord) object;

        return uuid.equals(rawRecord.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "RawRecord{" +
                "uuid='" + uuid + '\'' +
                ", record='" + record + '\'' +
                ", cloudflareIdentifier='" + cloudflareIdentifier + '\'' +
                ", expiryInstant='" + expiryInstant + '\'' +
                ", joins=" + joins +
                '}';
    }
}
