package me.piggypiglet.sample;

import me.piggypiglet.referrals.api.Referrals;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand implements CommandExecutor {
    private final Referrals api;

    public TestCommand(@NotNull final Referrals api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 1) {
            sendMessage(sender, "learn how to use this command noob");
            return true;
        }

        if (sender instanceof Player player) {
            final UUID uuid = player.getUniqueId();

            switch (args[0].toLowerCase()) {
                case "create" -> {
                    sendMessage(sender, "Creating record, just a sec.");

                    api.createRecord(uuid, player.getName()).thenAccept(record -> {
                        final Instant now = Instant.now();
                        final Instant then = record.expiryInstant();
                        final long minutes = TimeUnit.SECONDS.toMinutes(then.getEpochSecond() - now.getEpochSecond());

                        sendMessage(sender, "Successfully made your record :), %s. It'll expire in %s minute(s).", record.record(), minutes);
                    });
                }

                case "delete" -> {
                    sendMessage(sender, "You boomer, why you deleting your record? Anyway, 2 secs.");

                    api.deleteRecord(uuid)
                            .thenAccept(literallyNothing -> sendMessage(sender, "Well, are you happy? It's gone, you sour, terrible person."));
                }

                case "joins" ->
                        sendMessage(sender, "So you want to find out your joins huh? Ok, you have %s joins :D", api.getTotalReferrals(uuid));

                case "increment" -> {
                    final int joins = api.getTotalReferrals(uuid);
                    api.incrementReferral(uuid, null);
                    sendMessage(sender, "What? %s join(s) not enough for you? You bloody cheater, fine, I'll add one. There, you now have %s join(s).", joins, api.getTotalReferrals(uuid));
                }

                case "decrement" -> {
                    final int joins = api.getTotalReferrals(uuid);
                    api.decrementReferral(uuid, null);
                    sendMessage(sender, "Uh, why? Most people would be happy with %s join(s), but sure ig, you now have %s join(s).", joins, api.getTotalReferrals(uuid));
                }

                case "set" -> {
                    if (args.length < 2) {
                        sendMessage(sender, "Sorry bud, you need to also provide a value.");
                        return true;
                    }

                    final int value;

                    try {
                        value = Integer.parseInt(args[1]);
                    } catch (Exception exception) {
                        sendMessage(sender, "%s isn't a valid number... What you trying to do boi", args[1]);
                        return true;
                    }

                    final int joins = api.getTotalReferrals(uuid);
                    api.setReferrals(uuid, value);
                    sendMessage(sender, "No problemo, you've went from %s to %s join(s).", joins, api.getTotalReferrals(uuid));
                }

                case "top" -> {
                    final String top = api.getTopReferrals().entrySet().stream()
                            .limit(10)
                            .map(entry -> entry.getKey() + " - " + entry.getValue())
                            .collect(Collectors.joining("\n"));

                    sendMessage(sender, top);
                }
            }
        }

        return true;
    }

    private static void sendMessage(@NotNull final CommandSender sender, @NotNull final String message,
                                    @NotNull final Object... variables) {
        sender.sendMessage(String.format(message, variables));
    }
}
