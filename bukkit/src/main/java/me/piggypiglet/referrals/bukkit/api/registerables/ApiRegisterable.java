package me.piggypiglet.referrals.bukkit.api.registerables;

import com.google.inject.Inject;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ApiRegisterable extends Registerable {
    private final Referrals api;
    private final JavaPlugin main;

    @Inject
    public ApiRegisterable(@NotNull final Referrals api, @NotNull final JavaPlugin main) {
        this.api = api;
        this.main = main;
    }

    @Override
    public void execute() {
        Bukkit.getServicesManager().register(Referrals.class, api, main, ServicePriority.Normal);
    }
}
