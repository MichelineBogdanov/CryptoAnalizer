package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.controller.Controller;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.IOException;

public class Application {

    private final Controller controller;

    public Application() {
        this.controller = new Controller();
    }

    public Result run(Input input) throws IOException {
        return controller.run(input);
    }
}
