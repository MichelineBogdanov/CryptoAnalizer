package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.controller.Controller;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
import ru.javarush.bogdanov.cryptoanalizer.functions.Action;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.IOException;

public class Application {

    private final Controller controller;

    public Application() {
        this.controller = new Controller();
    }

    public Result run(String[] parameters) throws IOException {
        return controller.run(parameters);
    }
}
