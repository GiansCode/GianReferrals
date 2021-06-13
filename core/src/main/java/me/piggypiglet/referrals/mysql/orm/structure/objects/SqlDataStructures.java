package me.piggypiglet.referrals.mysql.orm.structure.objects;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public enum SqlDataStructures {
    TEXT(767, String.class),
    // -1 as we never use int as a identifier in the code
    INT(-1, int.class);

    private static final Map<Class<?>, SqlDataStructures> MAP = new HashMap<>();

    static {
        for (final SqlDataStructures dataStructure : values()) {
            for (final Class<?> type : dataStructure.types()) {
                MAP.put(type, dataStructure);
            }
        }
    }

    private final int length;
    private final Set<Class<?>> types;

    SqlDataStructures(final int length, @NotNull final Class<?> @NotNull ... types) {
        this.length = length;
        this.types = Set.of(types);
    }

    public int length() {
        return length;
    }

    @NotNull
    public Set<Class<?>> types() {
        return types;
    }

    @NotNull
    public static Optional<SqlDataStructures> fromType(@NotNull final Class<?> type) {
        return Optional.ofNullable(MAP.getOrDefault(type, null));
    }
}
