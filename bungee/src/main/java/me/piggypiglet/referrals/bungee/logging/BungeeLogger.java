package me.piggypiglet.referrals.bungee.logging;

import com.google.inject.Inject;
import me.piggypiglet.referrals.logging.Logger;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeLogger implements Logger {
    private final java.util.logging.Logger logger;

    @Inject
    public BungeeLogger(@NotNull final Plugin main) {
        this.logger = main.getLogger();
    }

    @Override
    public void info(final @NotNull String message) {
        logger.info(message);
    }

    @Override
    public void warn(final @NotNull String message) {
        logger.warning(message);
    }

    @Override
    public void error(final @NotNull String message) {
        logger.severe(message);
    }

    @Override
    public void error(final @NotNull String message, final @NotNull Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}
