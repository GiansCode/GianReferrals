package me.piggypiglet.referrals.mysql.tables;

import me.piggypiglet.referrals.mysql.orm.annotations.DataStructure;
import me.piggypiglet.referrals.mysql.orm.annotations.Identifier;
import me.piggypiglet.referrals.mysql.orm.annotations.Length;
import me.piggypiglet.referrals.mysql.orm.annotations.Table;
import me.piggypiglet.referrals.mysql.orm.structure.objects.SqlDataStructures;
import me.piggypiglet.referrals.mysql.tables.framework.RawObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@Table("referrals_joins")
public final class RawRecordJoins implements RawObject {
    @Identifier @Length(36) @DataStructure(SqlDataStructures.VARCHAR)
    private final String uuid;
    @Identifier @Length(36) @DataStructure(SqlDataStructures.VARCHAR)
    private final String joinerUuid;

    public RawRecordJoins(@NotNull final String uuid, @NotNull final String joinerUuid) {
        this.uuid = uuid;
        this.joinerUuid = joinerUuid;
    }

    @NotNull
    public String uuid() {
        return uuid;
    }

    @NotNull
    public String joinerUuid() {
        return joinerUuid;
    }

    @Override
    public boolean actualEquals(final @Nullable Object o) {
        return equals(o);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RawRecordJoins that = (RawRecordJoins) o;
        return uuid.equals(that.uuid) && joinerUuid.equals(that.joinerUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "RawRecordJoins{" +
                "uuid='" + uuid + '\'' +
                ", joinerUuid='" + joinerUuid + '\'' +
                '}';
    }
}
