package me.piggypiglet.gianreferrals.scanning.rules;

import me.piggypiglet.gianreferrals.scanning.rules.element.ElementWrapper;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class RuleUtils {
    private RuleUtils() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static boolean match(@NotNull final ElementWrapper element, @NotNull final Rules rules) {
        return rules.getRules().stream()
                .allMatch(rule -> rule.test(element));
    }
}
