package me.piggypiglet.sample;

import com.google.common.collect.Streams;
import com.google.common.io.CharStreams;
import me.piggypiglet.gianreferrals.bootstrap.GianReferralsBootstrap;
import me.piggypiglet.gianreferrals.config.Config;
import me.piggypiglet.gianreferrals.config.MysqlDetails;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class SamplePlugin extends JavaPlugin {
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
                .domain("test.p1g.pw")
                .zone("p1g.pw")
                .mysql(MysqlDetails.builder()
                        .host("127.0.0.1")
                        .username("referrals")
                        .password("test1234")
                        .database("referrals")
                        .tablePrefix("gr_")
                        .poolSize(10)
                        .port(3306)
                        .build())
                .build();
        GianReferralsBootstrap.initialize(config, this);
    }
}
