package me.piggypiglet.gianreferrals.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.piggypiglet.gianreferrals.bootstrap.GianReferralsBootstrap;
import me.piggypiglet.gianreferrals.config.Config;
import me.piggypiglet.gianreferrals.logging.Logger;
import me.piggypiglet.gianreferrals.scanning.framework.Scanner;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final GianReferralsBootstrap bootstrap;
    private final Scanner scanner;
    private final Config config;
    private final Class<?> mainClass;
    private final Object main;

    public InitialModule(@NotNull final GianReferralsBootstrap bootstrap, @NotNull final Scanner scanner,
                         @NotNull final Config config, @NotNull final Class<?> mainClass,
                         @NotNull final Object main) {
        this.bootstrap = bootstrap;
        this.scanner = scanner;
        this.config = config;
        this.mainClass = mainClass;
        this.main = main;
    }

    @Override
    protected void configure() {
        bind(mainClass, main);
    }

    @SuppressWarnings("unchecked")
    private <T> void bind(@NotNull final Class<?> clazz, @NotNull final Object instance) {
        bind((Class<? super T>) clazz).toInstance((T) instance);
    }

    @Provides
    @Singleton
    public GianReferralsBootstrap providesBootstrap() {
        return bootstrap;
    }

    @Provides
    @Singleton
    public Scanner providesScanner() {
        return scanner;
    }

    @Provides
    @Singleton
    public Config providesConfig() {
        return config;
    }
}
