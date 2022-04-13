package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateException;
import ru.javarush.bogdanov.cryptoanalizer.functions.*;

public enum Entyties {
    ENCRYPT(new Encryptor()),
    DECRYPT(new Decryptor()),
    BRUTEFORCE(new BruteForce()),
    STATICANALYS(new StaticAnalys()),
    EXIT(new Exit());

    private final Action action;

    Entyties(Action action) {
        this.action = action;
    }

    public static Action get(String actionName) {
        try {
            Entyties value = Entyties.valueOf(actionName.toUpperCase());
            return value.action;
        } catch (IllegalArgumentException e) {
            throw new ValidateException("Не получается запустить функцию!");
        }
    }
}
