package me.piggypiglet.referrals.bungee.task;

import com.google.inject.Inject;
import me.piggypiglet.referrals.task.Task;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeTask implements Task {
    private static final ExecutorService SYNC_EXECUTOR = Executors.newSingleThreadExecutor();

    private final TaskScheduler scheduler;
    private final Plugin main;

    @Inject
    public BungeeTask(@NotNull final Plugin main) {
        this.scheduler = main.getProxy().getScheduler();
        this.main = main;
    }

    @Override
    public void async(final @NotNull Runnable task) {
        scheduler.runAsync(main, task);
    }

    @Override
    public void async(final @NotNull Runnable task, final long delay, final long period) {
        scheduler.schedule(main, task, delay * 50, period * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void sync(final @NotNull Runnable task) {
        SYNC_EXECUTOR.submit(task);
    }
}
