package me.piggypiglet.referrals.task.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.referrals.bootstrap.framework.Registerable;
import me.piggypiglet.referrals.scanning.framework.Scanner;
import me.piggypiglet.referrals.scanning.rules.Rules;
import me.piggypiglet.referrals.task.Task;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
public final class TaskRegisterable extends Registerable {
    private final Scanner scanner;

    @Inject
    public TaskRegisterable(@NotNull final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        addBinding(Task.class, scanner.getClasses(Rules.builder().typeExtends(Task.class).disallowMutableClasses().build())
                .map(injector::getInstance)
                .map(Task.class::cast)
                .findAny().orElseThrow(() -> new AssertionError("Could not find a task implementation.")));
    }
}
