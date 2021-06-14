package me.piggypiglet.sample;

import me.piggypiglet.referrals.api.Referrals;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    private final Referrals api;

    public TestCommand(@NotNull final Referrals api) {
        super("gtest");
        this.api = api;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        api.createRecord(player.getUniqueId(), player.getName());
    }
}
