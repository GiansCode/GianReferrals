package me.piggypiglet.referrals.mysql.tables.framework;

import org.jetbrains.annotations.Nullable;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public interface RawObject {
    boolean actualEquals(@Nullable final Object object);
}
