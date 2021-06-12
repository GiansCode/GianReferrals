package me.piggypiglet.gianreferrals.cloudflare;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import eu.roboflax.cloudflare.CloudflareAccess;
import eu.roboflax.cloudflare.CloudflareRequest;
import eu.roboflax.cloudflare.constants.Category;
import eu.roboflax.cloudflare.objects.dns.DNSRecord;
import me.piggypiglet.gianreferrals.cloudflare.record.RecordCreateCallback;
import me.piggypiglet.gianreferrals.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class CloudflareRecordManager {
    private static final Gson GSON = new Gson();

    private final CloudflareAccess access;
    private final Config config;
    private final String zoneId;

    private final RecordCreateCallback recordCallback;

    @Inject
    public CloudflareRecordManager(@NotNull final CloudflareAccess access, @NotNull final Config config,
                                   @NotNull @Named("zone") final String zoneId, @NotNull final RecordCreateCallback recordCallback) {
        this.access = access;
        this.config = config;
        this.zoneId = zoneId;

        this.recordCallback = recordCallback;
    }


    public void addRecord(@NotNull final String name) {
        new CloudflareRequest(Category.CREATE_DNS_RECORD, access)
                .identifiers(zoneId)
                .body(GSON.toJsonTree(Map.of(
                        "type", "A",
                        "name", name + '.' + config.domain().replace('.' + config.zone(), ""),
                        "content", config.ip(),
                        "ttl", 1
                )))
                .asObjectAsync(recordCallback, DNSRecord.class);
    }
}
