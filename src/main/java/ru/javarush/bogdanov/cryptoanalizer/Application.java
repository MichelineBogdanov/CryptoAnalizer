package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.controller.Controller;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class Application {

    private final Controller controller;

    public Application() {
        this.controller = new Controller();
    }

    public Result run(String[] parameters) {
        controller.run(parameters);
        return null;
    }
}
