package me.piggypiglet.gianreferrals.guice.modules;

import com.google.inject.AbstractModule;
import me.piggypiglet.gianreferrals.bootstrap.GianReferralsBootstrap;
import me.piggypiglet.gianreferrals.scanning.framework.Scanner;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final GianReferralsBootstrap bootstrap;
    private final Scanner scanner;

    public InitialModule(@NotNull final GianReferralsBootstrap bootstrap, @NotNull final Scanner scanner) {
        this.bootstrap = bootstrap;
        this.scanner = scanner;
    }

    @Override
    protected void configure() {
        bind(GianReferralsBootstrap.class).toInstance(bootstrap);
        bind(Scanner.class).toInstance(scanner);
    }
}
