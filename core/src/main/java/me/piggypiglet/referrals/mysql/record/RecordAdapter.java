package me.piggypiglet.referrals.mysql.record;

import com.google.inject.Inject;
import me.piggypiglet.referrals.mysql.dbo.framework.DatabaseObjectAdapter;
import me.piggypiglet.referrals.mysql.dbo.framework.ModificationRequest;
import me.piggypiglet.referrals.mysql.tables.RawRecord;
import me.piggypiglet.referrals.mysql.tables.RawRecordJoins;
import me.piggypiglet.referrals.mysql.tables.framework.RawObject;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class RecordAdapter implements DatabaseObjectAdapter<Record> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private final Set<RawRecord> records;
    private final Set<RawRecordJoins> recordJoins;

    @Inject
    public RecordAdapter(@NotNull final Set<RawRecord> records, @NotNull final Set<RawRecordJoins> recordJoins) {
        this.records = records;
        this.recordJoins = recordJoins;
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
                        .joiners(recordJoins.stream()
                                .filter(rawRecordJoins -> rawRecordJoins.uuid().equals(rawRecord.uuid()))
                                .map(RawRecordJoins::joinerUuid)
                                .map(UUID::fromString)
                                .collect(Collectors.toSet()))
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
        final Set<RawRecordJoins> rawRecordJoins = record.joiners().stream()
                .map(uuid -> new RawRecordJoins(rawRecord.uuid(), uuid.toString()))
                .collect(Collectors.toSet());

        final Set<Object> modified = new HashSet<>();

        addIfAdded(records, modified, rawRecord);
        rawRecordJoins.forEach(object -> addIfAdded(recordJoins, modified, object));

        final Set<Object> deleted = new HashSet<>();

        deleteIfDeleted(recordJoins, join -> join.uuid().equals(rawRecord.uuid()), rawRecordJoins, deleted);

        return new ModificationRequest(modified, deleted);
    }

    private static <T extends RawObject> void addIfAdded(@NotNull final Set<T> set, @NotNull final Set<Object> resultSet,
                                                         @NotNull final T object) {
        if (set.add(object)) {
            resultSet.add(object);
        } else {
            new HashSet<>(set).stream()
                    .filter(object::equals)
                    .filter(element -> !element.actualEquals(object))
                    .forEach(element -> {
                        set.remove(element);
                        set.add(object);
                        resultSet.add(object);
                    });
        }
    }

    private static <T> void deleteIfDeleted(@NotNull final Set<T> cached, @NotNull final Predicate<T> filter,
                                            @NotNull final Set<T> converted, @NotNull final Set<Object> deleted) {
        cached.removeIf(object -> {
            if (!filter.test(object)) {
                return false;
            }

            if (!converted.contains(object)) {
                deleted.add(object);
                return true;
            }

            return false;
        });
    }
}
