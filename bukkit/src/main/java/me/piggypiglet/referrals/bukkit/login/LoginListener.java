package me.piggypiglet.referrals.bukkit.login;

import com.google.inject.Inject;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.mysql.record.Record;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class LoginListener implements Listener {
    private final Config config;
    private final Set<Record> records;
    private final Referrals api;

    @Inject
    public LoginListener(@NotNull final Config config, @NotNull final Set<Record> records,
                         @NotNull final Referrals api) {
        this.config = config;
        this.records = records;
        this.api = api;
    }

    @EventHandler
    public void onPlayerLogin(@NotNull final PlayerLoginEvent event) {
        String host = event.getHostname().split(":")[0].toLowerCase();

        if (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }

        if (host.equals(config.domain()) || host.equals(config.ip())) {
            return;
        }

        for (final Record record : records) {
            if (record.record().equalsIgnoreCase(host)) {
                final UUID ownerUuid = record.uuid();

//                if (ownerUuid.equals(event.getPlayer().getUniqueId())) {
//                    return;
//                }

                api.incrementReferral(ownerUuid);
                return;
            }
        }
    }
}
