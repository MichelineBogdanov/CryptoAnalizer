package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.functions.*;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.util.Arrays;

public class Controller {

    Validator validator = new Validator();

    public Result run(String[] parameters) {
        Action result = switch (parameters[0]) {
            case "1" -> new Encryptor();
            case "2" -> new Decryptor();
            case "3" -> new BruteForce();
            case "4" -> new StaticAnalys();
            case "5" -> new Exit();
            default -> null;
        };
        if (!(result instanceof Exit)) {
            String[] datas = Arrays.copyOfRange(parameters, 1, parameters.length);
            validator.validate(datas);
            return result.execute(datas);
        } else {
            return result.execute(parameters);
        }
    }

}
