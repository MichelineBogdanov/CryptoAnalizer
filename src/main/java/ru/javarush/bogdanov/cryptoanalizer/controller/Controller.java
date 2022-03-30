package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.HelpClass;
import ru.javarush.bogdanov.cryptoanalizer.functions.*;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class Controller {

    public Result run(Input input) {
        Action result = switch (input.command) {
            case "1" -> new Encryptor(new HelpClass());
            case "2" -> new Decryptor(new HelpClass());
            case "3" -> new BruteForce(new Decryptor(new HelpClass()));
            case "4" -> new StaticAnalys(new HelpClass());
            case "exit" -> new Exit();
            default -> null;
        };
        return result.execute(input.data);
    }
}
