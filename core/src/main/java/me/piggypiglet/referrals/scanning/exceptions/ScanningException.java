package me.piggypiglet.referrals.scanning.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class ScanningException extends RuntimeException {
    public ScanningException(@NotNull final Exception exception) {
        super(exception);
    }
}
