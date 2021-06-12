package me.piggypiglet.gianreferrals.mysql.record;

import com.google.inject.Inject;
import me.piggypiglet.gianreferrals.mysql.dbo.framework.DatabaseObjectAdapter;
import me.piggypiglet.gianreferrals.mysql.dbo.framework.ModificationRequest;
import me.piggypiglet.gianreferrals.mysql.tables.RawRecord;
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
                .map(rawRecord -> new Record(
                        UUID.fromString(rawRecord.uuid()),
                        rawRecord.record(),
                        Instant.from(TIME_FORMATTER.parse(rawRecord.expiryInstant())),
                        rawRecord.joins()
                )).collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public ModificationRequest applyToRaw(final @NotNull Record record) {
        final RawRecord rawRecord = new RawRecord(record.uuid().toString(), record.record(), record.expiryInstant().toString(), record.joins());

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
