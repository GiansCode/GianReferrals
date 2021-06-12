package me.piggypiglet.gianreferrals.config.expire;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------

/**
 * Modes of expiration.
 */
public enum ExpirationPolicy {
    /**
     * Record will expire after duration from when it's created.
     */
    CREATED,

    /**
     * Record will expire after duration from when it was last accessed.
     */
    ACCESSED
}
