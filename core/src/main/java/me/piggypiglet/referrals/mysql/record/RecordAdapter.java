package me.piggypiglet.referrals.mysql.record;

import com.google.inject.Inject;
import me.piggypiglet.referrals.mysql.dbo.framework.DatabaseObjectAdapter;
import me.piggypiglet.referrals.mysql.dbo.framework.ModificationRequest;
import me.piggypiglet.referrals.mysql.tables.RawRecord;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class RecordAdapter implements DatabaseObjectAdapter<Record> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private final Set<RawRecord> records;

    @Inject
    public RecordAdapter(@NotNull final Set<RawRecord> records) {
        this.records = records;
    }

    @NotNull
    @Override
    public Set<Record> loadFromRaw() {
        return records.stream()
                .map(rawRecord -> Record.builder()
                        .uuid(UUID.fromString(rawRecord.uuid()))
                        .record(rawRecord.record())
                        .cloudflareIdentifier(rawRecord.cloudflareIdentifier())
                        .expiryInstant(Instant.from(TIME_FORMATTER.parse(rawRecord.expiryInstant())))
                        .joins(rawRecord.joins())
                        .build()
                ).collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public Object convertToRawObject(final @NotNull Record record) {
        return new RawRecord(record.uuid().toString(), record.record(), record.cloudflareIdentifier(), record.expiryInstant().toString(), record.joins());
    }

    @NotNull
    @Override
    public ModificationRequest applyToRaw(final @NotNull Record record) {
        final RawRecord rawRecord = (RawRecord) convertToRawObject(record);
        final Set<Object> modified = new HashSet<>();

        if (records.add(rawRecord)) {
            modified.add(rawRecord);
        } else {
            new HashSet<>(records).stream()
                    .filter(rawRecord::equals)
                    .filter(element -> !element.actualEquals(rawRecord))
                    .forEach(element -> {
                        records.remove(element);
                        records.add(rawRecord);
                        modified.add(rawRecord);
                    });

        }

        return new ModificationRequest(modified, Collections.emptySet());
    }
}
