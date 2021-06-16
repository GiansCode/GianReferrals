package me.piggypiglet.referrals.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.piggypiglet.referrals.api.Referrals;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ReferralsExpansion extends PlaceholderExpansion {
    private static final Referrals API;

    static {
        API = Bukkit.getServer().getServicesManager().load(Referrals.class);

        if (API == null) {
            throw new AssertionError("Could not find Referrals API on the bukkit services manager, did you shade the dependency correctly?");
        }
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "referrals";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "PiggyPiglet";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(final OfflinePlayer player, @NotNull final String paramsString) {
        if (API == null) {
            return null;
        }

        final String[] params = paramsString.split("_");

        if (params.length < 1) {
            return null;
        }

        switch (params[0]) {
            case "joins" -> {
                return String.valueOf(API.getTotalReferrals(player.getUniqueId()));
            }

            case "top" -> {
                int limit = 10;

                if (params.length > 1) {
                    try {
                        limit = Integer.parseInt(params[1]);
                    } catch (Exception ignored) {}
                }

                return API.getTopReferrals().entrySet().stream()
                        .limit(limit)
                        .map(entry -> entry.getKey() + " - " + entry.getValue())
                        .collect(Collectors.joining("\n"));
            }
        }

        return null;
    }
}
