package me.piggypiglet.referrals.task;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface Task {
    void async(@NotNull final Runnable task);

    void async(@NotNull final Runnable task, final long delay, final long period);

    void sync(@NotNull final Runnable task);
}
