package me.piggypiglet.referrals.logging;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class LoggerRegisterable extends Registerable {
    private final Scanner scanner;

    @Inject
    public LoggerRegisterable(@NotNull final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        addBinding(Logger.class, scanner.getClasses(Rules.builder().typeExtends(Logger.class).disallowMutableClasses().build())
                .map(injector::getInstance)
                .map(Logger.class::cast)
                .findAny().orElseThrow(() -> new AssertionError("Could not find a logger implementation.")));
    }
}
