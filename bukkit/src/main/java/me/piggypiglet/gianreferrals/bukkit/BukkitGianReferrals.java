package me.piggypiglet.gianreferrals.bukkit;

import me.piggypiglet.gianreferrals.bootstrap.GianReferralsBootstrap;
import me.piggypiglet.gianreferrals.bootstrap.framework.Registerable;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitGianReferrals extends GianReferralsBootstrap {
    @NotNull
    @Override
    protected List<Class<? extends Registerable>> provideRegisterables() {
        return List.of(Test.class);
    }

    @NotNull
    @Override
    protected Class<?> provideMainType() {
        return JavaPlugin.class;
    }
}
