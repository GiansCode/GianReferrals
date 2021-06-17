package me.piggypiglet.referrals.api.implementation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.referrals.api.ImmutableRecord;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.api.implementation.platform.PlatformEvent;
import me.piggypiglet.referrals.api.implementation.platform.PlatformReferrals;
import me.piggypiglet.referrals.cloudflare.CloudflareRecordManager;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.config.expire.ExpirationPolicy;
import me.piggypiglet.referrals.mysql.dbo.DatabaseObjects;
import me.piggypiglet.referrals.mysql.record.Record;
import me.piggypiglet.referrals.task.Task;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class ReferralsImplementation implements Referrals {
    private final Task task;
    private final Config config;
    private final Set<Record> records;
    private final DatabaseObjects database;
    private final CloudflareRecordManager cloudflareManager;
    private final PlatformReferrals platformReferrals;

    @Inject
    public ReferralsImplementation(@NotNull final Task task, @NotNull final Config config,
                                   @NotNull final Set<Record> records, @NotNull final DatabaseObjects database,
                                   @NotNull final CloudflareRecordManager cloudflareManager, @NotNull final PlatformReferrals platformReferrals) {
        this.task = task;
        this.config = config;
        this.records = records;
        this.database = database;
        this.cloudflareManager = cloudflareManager;
        this.platformReferrals = platformReferrals;
    }

    @NotNull
    @Override
    public CompletableFuture<ImmutableRecord> createRecord(final @NotNull UUID uuid, final @NotNull String name) {
        final String lowerName = name.toLowerCase();
        final CompletableFuture<ImmutableRecord> result = new CompletableFuture<>();

        task.async(() -> {
            final String cloudflareIdentifier = cloudflareManager.addRecord(lowerName);
            final Record record = Record.builder()
                    .uuid(uuid)
                    .record(lowerName + '.' + config.domain())
                    .cloudflareIdentifier(cloudflareIdentifier)
                    .expiryInstant(Instant.now().plus(config.expiry().expiryMinutes(), ChronoUnit.MINUTES))
                    .joins(0)
                    .build();

            records.add(record);
            database.save(record);

            final ImmutableRecord immutableRecord = makeImmutable(record);
            task.sync(() -> platformReferrals.fire(PlatformEvent.RECORD_CREATE, immutableRecord, immutableRecord));

            result.complete(immutableRecord);
        });

        return result;
    }

    @NotNull
    @Override
    public CompletableFuture<Void> deleteRecord(final @NotNull UUID uuid) {
        final CompletableFuture<Void> result = new CompletableFuture<>();

        task.sync(() -> findRecord(uuid).ifPresentOrElse(record -> {
            records.remove(record);

            final ImmutableRecord immutableRecord = makeImmutable(record);
            platformReferrals.fire(PlatformEvent.RECORD_DELETE, immutableRecord, immutableRecord);

            task.async(() -> {
                cloudflareManager.deleteRecord(record.cloudflareIdentifier());
                database.delete(record);
                result.complete(null);
            });
        }, () -> result.completeExceptionally(new UnsupportedOperationException("This user does not have a record."))));

        return result;
    }

    @Override
    public boolean hasRecord(final @NotNull UUID uuid) {
        return findRecord(uuid).isPresent();
    }

    @Override
    public int getTotalReferrals(final @NotNull UUID uuid) {
        return findRecord(uuid)
                .map(Record::joins)
                .orElse(-1);
    }

    @Override
    public void incrementReferral(final @NotNull UUID uuid) {
        setReferrals(uuid, record -> record.joins() + 1, PlatformEvent.REFERRAL_ADDENDUM);
    }

    @Override
    public void decrementReferral(final @NotNull UUID uuid) {
        setReferrals(uuid, record -> record.joins() - 1, PlatformEvent.REFERRAL_REMOVAL);
    }

    @Override
    public void setReferrals(final @NotNull UUID uuid, final int referrals) {
        setReferrals(uuid, record -> referrals, PlatformEvent.REFERRAL_SETTING);
    }

    private void setReferrals(@NotNull final UUID uuid, @NotNull final Function<Record, Integer> referrals,
                              @NotNull final PlatformEvent event) {
        findRecord(uuid).ifPresent(record -> {
            final ImmutableRecord original = makeImmutable(record);
            record.joins(referrals.apply(record));

            if (config.expiry().expire() && config.expiry().policy() == ExpirationPolicy.ACCESSED) {
                record.expiryInstant(Instant.now().plus(config.expiry().expiryMinutes(), ChronoUnit.MINUTES));
            }

            task.async(() -> database.save(record));
            task.sync(() -> platformReferrals.fire(event, original, makeImmutable(record)));
        });
    }

    @NotNull
    @Override
    public Map<UUID, Integer> getTopReferrals() {
        final Map<UUID, Integer> result = new LinkedHashMap<>();

        records.stream()
                .sorted(Comparator.comparingInt(Record::joins))
                .forEach(record -> result.put(record.uuid(), record.joins()));

        return result;
    }

    @NotNull
    private Optional<Record> findRecord(@NotNull final UUID uuid) {
        return records.stream()
                .filter(record -> record.uuid().equals(uuid))
                .findAny();
    }

    @NotNull
    private static ImmutableRecord makeImmutable(@NotNull final Record record) {
        return new ImmutableRecord(record.uuid(), record.record(), record.expiryInstant(), record.joins());
    }
}
