package me.piggypiglet.referrals.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.piggypiglet.referrals.bootstrap.ReferralsBootstrap;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final ReferralsBootstrap bootstrap;
    private final Scanner scanner;
    private final Config config;
    private final Key<?> mainKey;
    private final Object main;

    public InitialModule(@NotNull final ReferralsBootstrap bootstrap, @NotNull final Scanner scanner,
                         @NotNull final Config config, @NotNull final Key<?> mainKey,
                         @NotNull final Object main) {
        this.bootstrap = bootstrap;
        this.scanner = scanner;
        this.config = config;
        this.mainKey = mainKey;
        this.main = main;
    }

    @Override
    protected void configure() {
        bind(mainKey, main);
    }

    @SuppressWarnings("unchecked")
    private <T> void bind(@NotNull final Key<?> key, @NotNull final Object instance) {
        bind((Key<? super T>) key).toInstance((T) instance);
    }

    @Provides
    @Singleton
    public ReferralsBootstrap providesBootstrap() {
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
