package me.piggypiglet.sample;

import me.piggypiglet.referrals.api.ImmutableRecord;
import me.piggypiglet.referrals.bungee.api.events.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class TestListener implements Listener {
    private final Logger logger;

    public TestListener(@NotNull final Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onRecordCreate(@NotNull final RecordCreateEvent event) {
        final ImmutableRecord record = event.record();
        logger.info(":D, " + record.uuid() + " created a record, " + record.record());
    }

    @EventHandler
    public void onRecordDelete(@NotNull final RecordDeleteEvent event) {
        final ImmutableRecord record = event.record();
        logger.info("D:, some smelly person, " + record.uuid() + " deleted their record: " + record.record());
    }

    @EventHandler
    public void onReferralAddendum(@NotNull final ReferralAddendumEvent event) {
        final ImmutableRecord record = event.current();
        logger.info(record.uuid() + " got a join on their record " + record.record() + ", they now have " + record.joins() + " join(s).");
    }

    @EventHandler
    public void onReferralRemoval(@NotNull final ReferralRemovalEvent event) {
        final ImmutableRecord record = event.current();
        logger.info(record.uuid() + " somehow lost a join on their record " + record.record() + "... tf?? they now have " + record.joins() + "join(s).");
    }

    @EventHandler
    public void onReferralSetting(@NotNull final ReferralSettingEvent event) {
        final ImmutableRecord old = event.old();
        final ImmutableRecord record = event.current();
        logger.info(record.uuid() + " had their join count on their record " + record.record() + " set to " + record.joins() + " join(s) from " + old.joins() + " join(s).");
    }
}
