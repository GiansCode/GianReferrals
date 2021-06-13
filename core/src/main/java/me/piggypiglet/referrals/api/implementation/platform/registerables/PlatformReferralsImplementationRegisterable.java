package me.piggypiglet.referrals.api.implementation.platform.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.referrals.api.implementation.platform.PlatformReferrals;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class PlatformReferralsImplementationRegisterable extends Registerable {
    private final Scanner scanner;

    @Inject
    public PlatformReferralsImplementationRegisterable(@NotNull final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        addBinding(PlatformReferrals.class, scanner.getClasses(Rules.builder().typeExtends(PlatformReferrals.class).disallowMutableClasses().build())
                .map(injector::getInstance)
                .map(PlatformReferrals.class::cast)
                .findAny().orElseThrow(() -> new AssertionError("Could not find PlatformReferrals implementation.")));
    }
}
