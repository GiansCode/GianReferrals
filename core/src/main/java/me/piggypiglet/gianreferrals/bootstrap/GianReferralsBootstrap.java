package me.piggypiglet.gianreferrals.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.piggypiglet.gianreferrals.bootstrap.framework.Registerable;
import me.piggypiglet.gianreferrals.guice.ExceptionalInjector;
import me.piggypiglet.gianreferrals.guice.modules.InitialModule;
import me.piggypiglet.gianreferrals.scanning.framework.Scanner;
import me.piggypiglet.gianreferrals.scanning.implementations.ZISScanner;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public abstract class GianReferralsBootstrap {
    private static final Class<GianReferralsBootstrap> MAIN_CLASS = GianReferralsBootstrap.class;
    private static final String PACKAGE;

    static {
        final String pckg = MAIN_CLASS.getPackageName();
        PACKAGE = pckg.substring(0, pckg.lastIndexOf('.'));
    }

    private static final List<Class<? extends Registerable>> INITIAL_REGISTERABLES = List.of();

    private static final List<Class<? extends Registerable>> FINAL_REGISTERABLES = List.of();

    @NotNull
    protected abstract List<Class<? extends Registerable>> provideRegisterables();

    public void init() {
        final Scanner scanner = ZISScanner.create(MAIN_CLASS, PACKAGE);
        final InitialModule initialModule = new InitialModule(this, scanner);
        final AtomicReference<Injector> injector = new AtomicReference<>(new ExceptionalInjector(Guice.createInjector(initialModule)));

        final List<Class<? extends Registerable>> registerables = new ArrayList<>();
        registerables.addAll(INITIAL_REGISTERABLES);
        registerables.addAll(provideRegisterables());
        registerables.addAll(FINAL_REGISTERABLES);

        for (final Class<? extends Registerable> registerableClass : registerables) {
            final Registerable registerable = injector.get().getInstance(registerableClass);
            registerable.run(injector.get());

            registerable.createModule()
                    .map(injector.get()::createChildInjector)
                    .ifPresent(injector::set);
        }
    }
}
