package me.piggypiglet.referrals.bukkit;

import com.google.inject.Injector;
import com.google.inject.Key;
import me.piggypiglet.referrals.bootstrap.ReferralsBootstrap;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.bukkit.login.registerables.EventListenerRegisterable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitReferralsBootstrap extends ReferralsBootstrap {
    @NotNull
    @Override
    protected List<Class<? extends Registerable>> provideRegisterables() {
        return List.of(
                EventListenerRegisterable.class
        );
    }

    @NotNull
    @Override
    protected Key<?> provideMainKey() {
        return Key.get(JavaPlugin.class);
    }
}
