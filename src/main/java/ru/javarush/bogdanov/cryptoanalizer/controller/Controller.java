package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
import ru.javarush.bogdanov.cryptoanalizer.functions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Controller {

    //TODO доделать метод
    public Action run(String[] parameters) {
        String[] datas = Arrays.copyOfRange(parameters, 1, parameters.length);
        getValidate(datas);
        switch (parameters[0]) {
            case "1" -> new Encryptor(datas);
            case "2" -> new Decryptor(datas);
            case "3" -> new BruteForce(datas);
            case "4" -> new StaticAnalys(datas);
        }
        return null;
    }

    private void getValidate(String[] datas) {
        if (Files.notExists(Path.of(datas[0]))) {
            throw new ValidateExeption("Исходного файла нету! Пропиши путь к нему заново!");
        }
    }
}
