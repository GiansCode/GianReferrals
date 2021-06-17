package me.piggypiglet.referrals.api;

import com.google.inject.ImplementedBy;
import me.piggypiglet.referrals.api.implementation.ReferralsImplementation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@ImplementedBy(ReferralsImplementation.class)
public interface Referrals {
    /**
     * Creates a record for the player in the database, and cloudflare.
     * Record will be the player's name + the domain set in the referrals
     * config class. e.g., if domain was p1g.pw, and the name was PiggyPiglet,
     * the record created would be piggypiglet.p1g.pw. Records are case
     * insensitive.
     *
     * @param uuid Player UUID
     * @param name Player name
     * @return Immutable version of the created record.
     */
    @NotNull
    CompletableFuture<ImmutableRecord> createRecord(@NotNull final UUID uuid, @NotNull final String name);

    /**
     * Deletes the record corresponding to this player from the database
     * and cloudflare.
     *
     * @param uuid Player UUID
     * @return CompletableFuture to signal completeness
     */
    @NotNull
    CompletableFuture<Void> deleteRecord(@NotNull final UUID uuid);

    /**
     * Check if a player has a record.
     *
     * @param uuid Player UUID
     * @return Whether player has record
     */
    boolean hasRecord(@NotNull final UUID uuid);

    /**
     * Get the total referrals a player has made. Will return -1 if the
     * player doesn't have a referral record.
     *
     * @param uuid Player UUID
     * @return Total number of referrals.
     */
    int getTotalReferrals(@NotNull final UUID uuid);

    /**
     * Increment a player's referral count by 1 (i.e. referrals = current referrals + 1).
     *
     * @param uuid Player UUID
     */
    void incrementReferral(@NotNull final UUID uuid);

    /**
     * Decrement a player's referral count by 1 (i.e. referrals = current referrals - 1).
     *
     * @param uuid Player UUID
     */
    void decrementReferral(@NotNull final UUID uuid);

    /**
     * Set a player's referral count to an explicit number.
     *
     * @param uuid      Player UUID
     * @param referrals New referral count
     */
    void setReferrals(@NotNull final UUID uuid, final int referrals);

    /**
     * Get an ordered map of the users with the top referrals.
     * Key = Player UUID
     * Value = Referral count
     *
     * @return Map of top referrals
     */
    @NotNull
    Map<UUID, Integer> getTopReferrals();
}
