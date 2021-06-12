package me.piggypiglet.gianreferrals.cloudflare.zone.registerables;

import com.google.inject.Inject;
import com.google.inject.name.Names;
import eu.roboflax.cloudflare.CloudflareAccess;
import eu.roboflax.cloudflare.CloudflareCallback;
import eu.roboflax.cloudflare.CloudflareRequest;
import eu.roboflax.cloudflare.CloudflareResponse;
import eu.roboflax.cloudflare.constants.Category;
import eu.roboflax.cloudflare.objects.zone.Zone;
import me.piggypiglet.gianreferrals.bootstrap.framework.Registerable;
import me.piggypiglet.gianreferrals.cloudflare.zone.exceptions.UnknownZoneException;
import me.piggypiglet.gianreferrals.config.Config;
import me.piggypiglet.gianreferrals.logging.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class CloudflareZoneRegisterable extends Registerable {
    private final CloudflareAccess access;
    private final Config config;

    private final Logger logger;

    @Inject
    public CloudflareZoneRegisterable(@NotNull final CloudflareAccess access, @NotNull final Config config,
                                      @NotNull final Logger logger) {
        this.access = access;
        this.config = config;

        this.logger = logger;
    }

    @Override
    public void execute() {
        new CloudflareRequest(Category.LIST_ZONES, access)
                .asObjectList(new CloudflareCallback<>() {
                    @Override
                    public void onSuccess(@NotNull final CloudflareResponse<List<Zone>> response) {
                        logger.info("Successfully retrieved zones from cloudflare.");

                        response.getObject().stream()
                                .filter(zone -> zone.getName().equalsIgnoreCase(config.zone()))
                                .map(Zone::getId)
                                .findAny()
                                .ifPresentOrElse(
                                        zoneId -> addBinding(String.class, Names.named("zone"), zoneId),
                                        () -> {
                                            throw new UnknownZoneException("Couldn't find zone with name: " + config.zone());
                                        }
                                );
                    }

                    @Override
                    public void onFailure(@NotNull final Throwable t, final int statusCode,
                                          @NotNull final String statusMessage, @NotNull final Map<Integer, String> errors) {
                        logger.error("Something went wrong when retrieving zones: %s", errors);
                        throw new RuntimeException(t);
                    }
                }, Zone.class);
    }
}
