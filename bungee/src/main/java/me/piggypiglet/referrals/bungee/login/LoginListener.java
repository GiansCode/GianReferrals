package me.piggypiglet.referrals.bungee.login;

import com.google.inject.Inject;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.mysql.record.Record;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
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
    public void onLogin(@NotNull final LoginEvent event) {
        final String host = event.getConnection().getVirtualHost().getHostString();

        if (host.equals(config.domain()) || host.equals(config.ip())) {
            return;
        }

        for (final Record record : records) {
            if (record.record().equalsIgnoreCase(host)) {
                final UUID ownerUuid = record.uuid();

//                if (event.getConnection().getUniqueId())) {
//                    return;
//                }

                api.incrementReferral(ownerUuid);
                return;
            }
        }
    }
}
