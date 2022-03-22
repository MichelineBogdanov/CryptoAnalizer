package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    //проверяем существование исходного файла
    public boolean validateSrc(Input input) {
        return Files.notExists(Path.of(input.data[0]));
    }

    //проверяем существование выходного файла, если его нет создаем
    public boolean validateDest(Input input) {
        if (Files.notExists(Path.of(input.data[1]))) {
            try {
                Files.createFile(Path.of(input.data[1]));
            } catch (IOException e) {
                throw new ValidateExeption("Не удалось создать отсутствующий выходной файл!");
            }
        }
        return false;
    }

    //проверяем формат введенного значения ключа
    public boolean validateKey(Input input) {
        if (input.command.equals("1") || input.command.equals("2")) {
            try {
                Integer.parseInt(input.data[2]);
            } catch (NumberFormatException e) {
                System.out.println("Неправильный формат ключа!");
                return true;
            }
        }
        return false;
    }

    //проверяем существование словаря
    public boolean validateDictionary(Input input) {
        return Files.notExists(Path.of(input.data[2]));
    }
}
