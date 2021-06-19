package me.piggypiglet.referrals.bukkit.task;

import com.google.inject.Inject;
import me.piggypiglet.referrals.task.Task;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitTask implements Task {
    private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();

    private final JavaPlugin main;

    @Inject
    public BukkitTask(@NotNull final JavaPlugin main) {
        this.main = main;
    }

    @Override
    public void async(final @NotNull Runnable task) {
        SCHEDULER.runTaskAsynchronously(main, task);
    }

    @Override
    public void async(final @NotNull Runnable task, final long delay, final long period) {
        SCHEDULER.runTaskTimerAsynchronously(main, task, delay, period);
    }

    @Override
    public void sync(final @NotNull Runnable task) {
        SCHEDULER.runTask(main, task);
    }
}
