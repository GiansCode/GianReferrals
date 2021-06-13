package me.piggypiglet.referrals.guice.objects;

import com.google.inject.Key;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final record Binding<T>(Key<? super T> key, T instance) {
}
