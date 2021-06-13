package me.piggypiglet.referrals.bukkit.logging;

import com.google.inject.Inject;
import me.piggypiglet.referrals.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitLogger extends Logger {
    private final java.util.logging.Logger handle;

    @Inject
    public BukkitLogger(@NotNull final JavaPlugin main) {
        handle = main.getLogger();
    }

    @Override
    public void info(final @NotNull String message) {
        handle.info(message);
    }

    @Override
    public void warn(final @NotNull String message) {
        handle.warning(message);
    }

    @Override
    public void error(final @NotNull String message) {
        handle.severe(message);
    }

    @Override
    public void error(final @NotNull String message, final @NotNull Throwable throwable) {
        handle.log(Level.SEVERE, message, throwable);
    }
}
