package me.piggypiglet.referrals.bungee;

import com.google.inject.Injector;
import com.google.inject.Key;
import me.piggypiglet.referrals.bootstrap.ReferralsBootstrap;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.bungee.login.registerables.EventListenerRegisterable;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeReferralsBootstrap extends ReferralsBootstrap {
    @NotNull
    @Override
    protected List<Class<? extends Registerable>> provideRegisterables() {
        return List.of(
                EventListenerRegisterable.class
        );
    }

    @NotNull
    @Override
    protected Key<?> provideMainKey() {
        return Key.get(Plugin.class);
    }
}
