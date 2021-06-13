package me.piggypiglet.referrals.scanning.framework;

import me.piggypiglet.referrals.scanning.rules.Rules;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface Scanner {
    @NotNull
    Stream<Class<?>> getClasses(@NotNull final Rules rules);

    @NotNull
    Stream<Parameter> getParametersInConstructors(@NotNull final Rules rules);

    @NotNull
    Stream<Field> getFields(@NotNull final Rules rules);
}
