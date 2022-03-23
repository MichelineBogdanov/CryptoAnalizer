package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.controller.Controller;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class Application {

    private final Controller controller;

    public Application() {
        this.controller = new Controller();
    }

    public Result run(Input input) {
        return controller.run(input);
    }
}
