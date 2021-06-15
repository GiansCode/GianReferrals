package me.piggypiglet.referrals.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.api.implementation.platform.registerables.PlatformReferralsImplementationRegisterable;
import me.piggypiglet.referrals.bootstrap.exceptions.BootstrapHaltException;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.cloudflare.registerables.CloudflareRegisterable;
import me.piggypiglet.referrals.cloudflare.zone.registerables.CloudflareZoneRegisterable;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.expiration.ExpiryRegisterable;
import me.piggypiglet.referrals.guice.ExceptionalInjector;
import me.piggypiglet.referrals.guice.modules.InitialModule;
import me.piggypiglet.referrals.logging.LoggerRegisterable;
import me.piggypiglet.referrals.mysql.dbo.registerables.DatabaseObjectRegisterable;
import me.piggypiglet.referrals.mysql.orm.registerables.TableObjectsRegisterable;
import me.piggypiglet.referrals.mysql.orm.registerables.TablesRegisterable;
import me.piggypiglet.referrals.mysql.orm.structure.registerables.TableStructuresRegisterable;
import me.piggypiglet.referrals.mysql.registerables.MysqlRegisterable;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.implementations.ZISScanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import me.piggypiglet.referrals.task.registerables.TaskRegisterable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public abstract class ReferralsBootstrap {
    private static final Class<ReferralsBootstrap> MAIN_CLASS = ReferralsBootstrap.class;
    private static final String PACKAGE;

    static {
        // dynamic retrieval incase of relocation
        final String pckg = MAIN_CLASS.getPackageName();
        PACKAGE = pckg.substring(0, pckg.lastIndexOf('.')).replace('.', '/');
    }

    private static final List<Class<? extends Registerable>> INITIAL_REGISTERABLES = List.of(
            LoggerRegisterable.class,
            TaskRegisterable.class,

            CloudflareRegisterable.class,
            CloudflareZoneRegisterable.class,

            MysqlRegisterable.class,
            TableObjectsRegisterable.class,
            TableStructuresRegisterable.class,
            TablesRegisterable.class,
            DatabaseObjectRegisterable.class,

            PlatformReferralsImplementationRegisterable.class
    );

    private static final List<Class<? extends Registerable>> FINAL_REGISTERABLES = List.of(
            ExpiryRegisterable.class
    );

    @NotNull
    protected abstract List<Class<? extends Registerable>> provideRegisterables();

    @NotNull
    protected abstract Key<?> provideMainKey();

    /**
     * Initialize the API (connect to MySQL, cloudflare, etc).
     *
     * @param config Populated config instance, see {@link Config#builder}.
     * @param main Your main instance (JavaPlugin, Plugin, etc)
     * @return Referrals API instance
     */
    public static Referrals initialize(@NotNull final Config config, @NotNull final Object main) {
        final Scanner scanner = ZISScanner.create(MAIN_CLASS, PACKAGE);

        final ReferralsBootstrap bootstrap = scanner.getClasses(Rules.builder().typeExtends(MAIN_CLASS).disallowMutableClasses().build())
                .map(clazz -> {
                    try {
                        return (ReferralsBootstrap) clazz.getConstructors()[0].newInstance();
                    } catch (Exception exception) {
                        throw new AssertionError("Something went wrong when instantiating the main class.", exception);
                    }
                }).findAny().orElseThrow(() -> new AssertionError("Could not find a main class."));

        final InitialModule initialModule = new InitialModule(bootstrap, scanner, config, bootstrap.provideMainKey(), main);
        final AtomicReference<Injector> injector = new AtomicReference<>(new ExceptionalInjector(Guice.createInjector(initialModule)));

        final List<Class<? extends Registerable>> registerables = new ArrayList<>();
        registerables.addAll(INITIAL_REGISTERABLES);
        registerables.addAll(bootstrap.provideRegisterables());
        registerables.addAll(FINAL_REGISTERABLES);

        for (final Class<? extends Registerable> registerableClass : registerables) {
            final Registerable registerable = injector.get().getInstance(registerableClass);
            registerable.run(injector.get());

            final BootstrapHaltException halt = registerable.halt();

            if (halt != null) {
                throw halt;
            }

            registerable.createModule()
                    .map(injector.get()::createChildInjector)
                    .ifPresent(injector::set);
        }

        return injector.get().getInstance(Referrals.class);
    }
}
