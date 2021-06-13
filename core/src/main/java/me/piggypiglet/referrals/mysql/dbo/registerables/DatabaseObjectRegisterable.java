package me.piggypiglet.referrals.mysql.dbo.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.logging.Logger;
import me.piggypiglet.referrals.mysql.dbo.framework.DatabaseObjectAdapter;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class DatabaseObjectRegisterable extends Registerable {
    private static final Named ADAPTERS = Names.named("adapters");

    private final Scanner scanner;
    private final Logger logger;

    @Inject
    public DatabaseObjectRegisterable(@NotNull final Scanner scanner, @NotNull final Logger logger) {
        this.scanner = scanner;
        this.logger = logger;
    }

    @Override
    @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"})
    public void execute(final @NotNull Injector injector) {
        final Set<DatabaseObjectAdapter<?>> adapters = scanner.getClasses(Rules.builder().typeExtends(DatabaseObjectAdapter.class).disallowMutableClasses().build())
                .map(injector::getInstance)
                .map(adapter -> (DatabaseObjectAdapter<?>) adapter)
                .collect(Collectors.toSet());

        addBinding(new TypeLiteral<Set<DatabaseObjectAdapter<?>>>() {}, ADAPTERS, adapters);

        final Map<Class<?>, DatabaseObjectAdapter<?>> adapterMap = new HashMap<>();

        adapters.forEach(adapter -> {
            final Type type = ((ParameterizedType) Arrays.stream(adapter.getClass().getGenericInterfaces())
                    .filter(i -> TypeLiteral.get(i).getRawType() == DatabaseObjectAdapter.class)
                    .findAny().get()).getActualTypeArguments()[0];

            addBinding((Key<DatabaseObjectAdapter<?>>) Key.get(Types.newParameterizedType(DatabaseObjectAdapter.class, type)), adapter);

            final Set<?> set = adapter.loadFromRaw();
            logger.info("Loaded %s %s's.", set.size(), type.getTypeName());
            adapterMap.put(TypeLiteral.get(type).getRawType(), adapter);
            addBinding((Key<Set<?>>) Key.get(Types.setOf(type)), set);
        });

        addBinding(new TypeLiteral<Map<Class<?>, DatabaseObjectAdapter<?>>>() {}, ADAPTERS, adapterMap);
    }
}
