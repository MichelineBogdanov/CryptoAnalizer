package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.functions.*;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class Controller {

    public Result run(Input input) {
        Action result = Entyties.get(input.command);
        return result.execute(input.data);
    }
}
