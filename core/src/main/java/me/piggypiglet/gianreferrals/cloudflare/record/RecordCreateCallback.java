package me.piggypiglet.gianreferrals.cloudflare.record;

import com.google.inject.Inject;
import eu.roboflax.cloudflare.CloudflareCallback;
import eu.roboflax.cloudflare.CloudflareResponse;
import eu.roboflax.cloudflare.objects.dns.DNSRecord;
import me.piggypiglet.gianreferrals.logging.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class RecordCreateCallback implements CloudflareCallback<CloudflareResponse<DNSRecord>> {
    private final Logger logger;

    @Inject
    public RecordCreateCallback(@NotNull final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onSuccess(@NotNull final CloudflareResponse<DNSRecord> response) {
        logger.info("Created DNS record: %s - %s", response.getObject().getName(), response.getObject().getContent());
    }

    @Override
    public void onFailure(@NotNull final Throwable t, final int statusCode,
                          @NotNull final String statusMessage, @NotNull final Map<Integer, String> errors) {
        logger.error("Something went wrong when creating a DNS record in cloudflare: %s", errors);
    }
}
