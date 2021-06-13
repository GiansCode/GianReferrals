package me.piggypiglet.referrals.bukkit.api;

import me.piggypiglet.referrals.api.ImmutableRecord;
import me.piggypiglet.referrals.api.implementation.platform.PlatformEvent;
import me.piggypiglet.referrals.api.implementation.platform.PlatformReferrals;
import me.piggypiglet.referrals.bukkit.api.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitReferrals implements PlatformReferrals {
    private static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();

    @Override
    public void fire(final @NotNull PlatformEvent event, final @NotNull ImmutableRecord old,
                     @NotNull final ImmutableRecord current) {
        switch (event) {
            case RECORD_CREATE -> PLUGIN_MANAGER.callEvent(new RecordCreateEvent(current));
            case RECORD_DELETE -> PLUGIN_MANAGER.callEvent(new RecordDeleteEvent(current));
            case REFERRAL_ADDENDUM -> PLUGIN_MANAGER.callEvent(new ReferralAddendumEvent(old, current));
            case REFERRAL_REMOVAL -> PLUGIN_MANAGER.callEvent(new ReferralRemovalEvent(old, current));
            case REFERRAL_SETTING -> PLUGIN_MANAGER.callEvent(new ReferralSettingEvent(old, current));
        }
    }
}
