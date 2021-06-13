package me.piggypiglet.referrals.mysql.orm.registerables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.mysql.orm.TableManager;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class TablesRegisterable extends Registerable {
    private final Set<Class<?>> tables;
    private final TableManager tableManager;

    @Inject
    public TablesRegisterable(@NotNull @Named("tables") final Set<Class<?>> tables, @NotNull final TableManager tableManager) {
        this.tables = tables;
        this.tableManager = tableManager;
    }

    @Override
    public void execute() {
        tableManager.initialize();
        tables.forEach(tableManager::loadAll);
    }
}
