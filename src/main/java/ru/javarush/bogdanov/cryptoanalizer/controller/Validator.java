package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    void validate(String[] datas) {
        if (Files.notExists(Path.of(datas[0]))) {
            throw new ValidateExeption("Исходного файла нету! Пропиши путь к нему заново!");
        }
    }

}
