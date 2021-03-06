package me.piggypiglet.sample;

import com.google.common.io.CharStreams;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.bootstrap.ReferralsBootstrap;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.config.MysqlDetails;
import me.piggypiglet.referrals.config.expire.ExpirationPolicy;
import me.piggypiglet.referrals.config.expire.ExpiryOptions;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class SamplePlugin extends Plugin {
    private final String apiKey;

    {
        try (final Reader reader = new InputStreamReader(getClass().getResourceAsStream("/private"))) {
            apiKey = CharStreams.toString(reader);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onEnable() {
        final Config config = Config.builder()
                .apiKey(apiKey)
                .ip("127.0.0.1")
                .domain("p1g.pw")
                .zone("p1g.pw")
                .mysql(MysqlDetails.builder()
                        .host("127.0.0.1")
                        .username("referrals")
                        .password("test1234")
                        .database("referrals")
                        .tablePrefix("bungee_")
                        .poolSize(10)
                        .port(3306)
                        .build())
                .expiry(ExpiryOptions.builder()
                        .expire(true)
                        .policy(ExpirationPolicy.ACCESSED)
                        .expiryCheckPeriod(10, TimeUnit.SECONDS)
                        .expiry(1, TimeUnit.MINUTES)
                        .build())
                .build();
        final Referrals api = ReferralsBootstrap.initialize(config, this);

        getProxy().getPluginManager().registerListener(this, new TestListener(getLogger()));
        getProxy().getPluginManager().registerCommand(this, new TestCommand(api));
    }
}
