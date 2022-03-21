package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.functions.*;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class Controller {

    public Result run(Input input) {
        Action result = switch (input.command) {
            case "1" -> new Encryptor();
            case "2" -> new Decryptor();
            case "3" -> new BruteForce();
            case "4" -> new StaticAnalys();
            case "5" -> new Exit();
            default -> null;
        };
        return result.execute(input.data);
    }
}
