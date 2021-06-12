package me.piggypiglet.gianreferrals.mysql.dbo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.piggypiglet.gianreferrals.mysql.dbo.framework.DatabaseObjectAdapter;
import me.piggypiglet.gianreferrals.mysql.dbo.framework.ModificationRequest;
import me.piggypiglet.gianreferrals.mysql.orm.TableManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@SuppressWarnings("unchecked")
public final class DatabaseObjects {
    private final Map<Class<?>, DatabaseObjectAdapter<?>> adapters;
    private final TableManager tableManager;

    @Inject
    public DatabaseObjects(@NotNull @Named("adapters") final Map<Class<?>, DatabaseObjectAdapter<?>> adapters,
                           @NotNull final TableManager tableManager) {
        this.adapters = adapters;
        this.tableManager = tableManager;
    }

    public <T> void save(@NotNull final T object) {
        final ModificationRequest modificationRequest = ((DatabaseObjectAdapter<T>) adapters.get(object.getClass())).applyToRaw(object);

        modificationRequest.modified().forEach(tableManager::save);
        modificationRequest.deleted().forEach(tableManager::delete);
    }
}
