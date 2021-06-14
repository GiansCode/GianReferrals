package me.piggypiglet.referrals.bungee.login.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class EventListenerRegisterable extends Registerable {
    private final Scanner scanner;
    private final Plugin main;

    @Inject
    public EventListenerRegisterable(@NotNull final Scanner scanner, @NotNull final Plugin main) {
        this.scanner = scanner;
        this.main = main;
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        scanner.getClasses(Rules.builder().typeExtends(Listener.class).disallowMutableClasses().build())
                .map(injector::getInstance)
                .map(Listener.class::cast)
                .forEach(listener -> main.getProxy().getPluginManager().registerListener(main, listener));
    }
}
