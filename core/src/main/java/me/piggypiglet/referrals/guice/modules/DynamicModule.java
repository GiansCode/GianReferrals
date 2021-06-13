package me.piggypiglet.referrals.guice.modules;

import com.google.inject.AbstractModule;
import me.piggypiglet.referrals.guice.objects.Binding;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class DynamicModule extends AbstractModule {
    private final Set<Binding<?>> bindings;
    private final Class<?>[] staticInjections;

    public DynamicModule(@NotNull final Set<Binding<?>> bindings, @NotNull final Class<?>... staticInjections) {
        this.bindings = bindings;
        this.staticInjections = staticInjections;
    }

    @Override
    protected void configure() {
        bindings.forEach(this::bind);
        requestStaticInjection(staticInjections);
    }

    private <T> void bind(@NotNull final Binding<T> binding) {
        bind(binding.key()).toInstance(binding.instance());
    }
}
