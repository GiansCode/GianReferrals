package me.piggypiglet.referrals.cloudflare.registerables;

import com.google.inject.Inject;
import eu.roboflax.cloudflare.CloudflareAccess;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.config.Config;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class CloudflareRegisterable extends Registerable {
    private final Config config;

    @Inject
    public CloudflareRegisterable(@NotNull final Config config) {
        this.config = config;
    }

    @Override
    public void execute() {
        addBinding(CloudflareAccess.class, new CloudflareAccess(config.apiKey()));
    }
}
