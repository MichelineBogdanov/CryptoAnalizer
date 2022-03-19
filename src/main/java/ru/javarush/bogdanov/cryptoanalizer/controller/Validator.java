package ru.javarush.bogdanov.cryptoanalizer.controller;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    void validate(String[] datas) {
        if (Files.notExists(Path.of(datas[0]))) {
            throw new ValidateExeption("Исходного файла нету! Пропиши путь к нему заново!");
        }
        if (Files.notExists(Path.of(datas[1]))) {
            try {
                Files.createFile(Path.of(datas[1]));
            } catch (IOException e) {
                throw new ValidateExeption("Не удалось создать отсутствующий выходной файл!");
            }
        }
        if (Integer.parseInt(datas[2]) > Constants.ALPHABET.length()) {
            datas[2] = Integer.toString(Integer.parseInt(datas[2]) % Constants.ALPHABET.length());
        }
    }

}
