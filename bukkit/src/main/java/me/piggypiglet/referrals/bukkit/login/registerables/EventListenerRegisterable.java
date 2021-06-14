package me.piggypiglet.referrals.bukkit.login.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class EventListenerRegisterable extends Registerable {
    private final Scanner scanner;
    private final JavaPlugin main;

    @Inject
    public EventListenerRegisterable(@NotNull final Scanner scanner, @NotNull final JavaPlugin main) {
        this.scanner = scanner;
        this.main = main;
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        scanner.getClasses(Rules.builder().typeExtends(Listener.class).disallowMutableClasses().build())
                .map(injector::getInstance)
                .map(Listener.class::cast)
                .forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, main));
    }
}
