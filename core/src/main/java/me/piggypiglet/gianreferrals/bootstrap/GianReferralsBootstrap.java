package me.piggypiglet.gianreferrals.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.piggypiglet.gianreferrals.bootstrap.framework.Registerable;
import me.piggypiglet.gianreferrals.cloudflare.registerables.CloudflareRegisterable;
import me.piggypiglet.gianreferrals.cloudflare.zone.registerables.CloudflareZoneRegisterable;
import me.piggypiglet.gianreferrals.config.Config;
import me.piggypiglet.gianreferrals.guice.ExceptionalInjector;
import me.piggypiglet.gianreferrals.guice.modules.InitialModule;
import me.piggypiglet.gianreferrals.logging.Logger;
import me.piggypiglet.gianreferrals.logging.LoggerRegisterable;
import me.piggypiglet.gianreferrals.mysql.dbo.registerables.DatabaseObjectRegisterable;
import me.piggypiglet.gianreferrals.mysql.orm.registerables.TableObjectsRegisterable;
import me.piggypiglet.gianreferrals.mysql.orm.registerables.TablesRegisterable;
import me.piggypiglet.gianreferrals.mysql.orm.structure.registerables.TableStructuresRegisterable;
import me.piggypiglet.gianreferrals.mysql.registerables.MysqlRegisterable;
import me.piggypiglet.gianreferrals.scanning.framework.Scanner;
import me.piggypiglet.gianreferrals.scanning.implementations.ZISScanner;
import me.piggypiglet.gianreferrals.scanning.rules.Rules;
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
        // dynamic retrieval incase of relocation
        final String pckg = MAIN_CLASS.getPackageName();
        PACKAGE = pckg.substring(0, pckg.lastIndexOf('.')).replace('.', '/');
    }

    private static final List<Class<? extends Registerable>> INITIAL_REGISTERABLES = List.of(
            LoggerRegisterable.class,

            CloudflareRegisterable.class,
            CloudflareZoneRegisterable.class,

            MysqlRegisterable.class,
            TableObjectsRegisterable.class,
            TableStructuresRegisterable.class,
            TablesRegisterable.class,
            DatabaseObjectRegisterable.class
    );

    private static final List<Class<? extends Registerable>> FINAL_REGISTERABLES = List.of();

    @NotNull
    protected abstract List<Class<? extends Registerable>> provideRegisterables();

    @NotNull
    protected abstract Class<?> provideMainType();

    public static <T> void initialize(@NotNull final Config config, @NotNull final T main) {
        final Scanner scanner = ZISScanner.create(MAIN_CLASS, PACKAGE);

        final GianReferralsBootstrap bootstrap = scanner.getClasses(Rules.builder().typeExtends(MAIN_CLASS).disallowMutableClasses().build())
                .map(clazz -> {
                    try {
                        return (GianReferralsBootstrap) clazz.getConstructors()[0].newInstance();
                    } catch (Exception exception) {
                        throw new AssertionError("Something went wrong when instantiating the main class.", exception);
                    }
                }).findAny().orElseThrow(() -> new AssertionError("Could not find a main class."));

        final InitialModule initialModule = new InitialModule(bootstrap, scanner, config, bootstrap.provideMainType(), main);
        final AtomicReference<Injector> injector = new AtomicReference<>(new ExceptionalInjector(Guice.createInjector(initialModule)));

        final List<Class<? extends Registerable>> registerables = new ArrayList<>();
        registerables.addAll(INITIAL_REGISTERABLES);
        registerables.addAll(bootstrap.provideRegisterables());
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
