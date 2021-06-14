package me.piggypiglet.referrals.bungee.login;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class LoginListener implements Listener {
    @EventHandler
    public void onLogin(@NotNull final LoginEvent event) {
        String host = event.getConnection().getVirtualHost().toString();

        System.out.println(host);
    }
}
