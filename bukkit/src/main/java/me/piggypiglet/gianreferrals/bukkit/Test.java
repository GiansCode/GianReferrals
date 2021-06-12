package me.piggypiglet.gianreferrals.bukkit;

import com.google.inject.Inject;
import me.piggypiglet.gianreferrals.bootstrap.framework.Registerable;
import me.piggypiglet.gianreferrals.cloudflare.CloudflareRecordManager;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends Registerable {
    private final CloudflareRecordManager cloudflareRecordManager;

    @Inject
    public Test(@NotNull final CloudflareRecordManager cloudflareRecordManager) {
        this.cloudflareRecordManager = cloudflareRecordManager;
    }

    @Override
    public void execute() {
        cloudflareRecordManager.addRecord("yoyoyo");
    }
}
