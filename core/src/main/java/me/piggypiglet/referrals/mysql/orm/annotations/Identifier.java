package me.piggypiglet.referrals.mysql.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2021
// https://www.piggypiglet.me
// ------------------------------
@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME)
public @interface Identifier {
}
