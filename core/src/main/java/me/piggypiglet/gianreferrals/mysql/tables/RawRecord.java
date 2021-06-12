package me.piggypiglet.gianreferrals.mysql.tables;

import me.piggypiglet.gianreferrals.mysql.orm.annotations.Identifier;
import me.piggypiglet.gianreferrals.mysql.orm.annotations.Length;
import me.piggypiglet.gianreferrals.mysql.orm.annotations.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@Table("records")
public final record RawRecord(@Identifier @Length(36) String uuid, String record,
                              String expiryInstant, int joins) {
    public boolean actualEquals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final RawRecord rawRecord = (RawRecord) object;

        return uuid.equals(rawRecord.uuid) && record.equals(rawRecord.record)
                && expiryInstant.equals(rawRecord.expiryInstant) && joins == rawRecord.joins;
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
                ", expiryInstant='" + expiryInstant + '\'' +
                ", joins=" + joins +
                '}';
    }
}
