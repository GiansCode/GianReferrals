package me.piggypiglet.referrals.expiration;

import com.google.inject.Inject;
import me.piggypiglet.referrals.api.Referrals;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.config.Config;
import me.piggypiglet.referrals.config.expire.ExpiryOptions;
import me.piggypiglet.referrals.mysql.record.Record;
import me.piggypiglet.referrals.task.Task;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ExpiryRegisterable extends Registerable {
    private final Task task;
    private final Set<Record> records;
    private final ExpiryOptions expiry;
    private final Referrals api;

    @Inject
    public ExpiryRegisterable(@NotNull final Task task, @NotNull final Set<Record> records,
                              @NotNull final Config config, @NotNull final Referrals api) {
        this.task = task;
        this.records = records;
        this.expiry = config.expiry();
        this.api = api;
    }

    @Override
    public void execute() {
        if (!expiry.expire()) {
            return;
        }

        task.async(() -> {
            final Instant now = Instant.now();

            for (final Record record : new HashSet<>(records)) {
                if (record.expiryInstant().isBefore(now)) {
                    api.deleteRecord(record.uuid());
                }
            }
        }, 0, expiry.expiryCheckPeriodMinutes());
    }
}
