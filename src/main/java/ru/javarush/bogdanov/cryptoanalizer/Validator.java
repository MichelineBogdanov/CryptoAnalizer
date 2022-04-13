package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateException;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    //валидатор для шифратора и дешифратора
    public boolean validateDecryptorEncryptor(Input input) {
        return validateCountDataForDecryptorEncryptorStatAn(input) || validateFilePath(input.data[0]) || validateDest(input) || validateKey(input);
    }

    //валидатор для brute force
    public boolean validateBruteForce(Input input) {
        return validateCountDataForBruteForce(input) || validateFilePath(input.data[0]) || validateDest(input);
    }

    //валидатор для статистического анализатора
    public boolean validateStaticAnalyser(Input input) {
        return validateCountDataForDecryptorEncryptorStatAn(input) || validateFilePath(input.data[0]) || validateDest(input) || validateFilePath(input.data[2]);
    }

    //проверяем количество введенных данных для шифратора и дешифратора и статистического анализатора
    private boolean validateCountDataForDecryptorEncryptorStatAn(Input input) {
        if (input.data.length < 3) {
            System.out.println("Не хватает данных!");
        } else if (input.data.length > 3) {
            System.out.println("Слишком много данных!");
        }
        return input.data.length != 3;
    }

    //проверяем количество введенных данных для шифратора и дешифратора
    private boolean validateCountDataForBruteForce(Input input) {
        if (input.data.length < 2) {
            System.out.println("Не хватает данных!");
        } else if (input.data.length > 2) {
            System.out.println("Слишком много данных!");
        }
        return input.data.length != 2;
    }

    //проверяем существование исходного файла
    private boolean validateFilePath(String inputPath) {
        if (Files.notExists(Path.of(inputPath))) {
            System.out.println("Какого-то файла не существует!");
            return true;
        }
        return false;
    }

    //проверяем существование выходного файла, если его нет создаем
    private boolean validateDest(Input input) {
        if (Files.notExists(Path.of(input.data[1]))) {
            try {
                Files.createFile(Path.of(input.data[1]));
            } catch (IOException e) {
                throw new ValidateException("Не удалось создать отсутствующий выходной файл!");
            }
        }
        return false;
    }

    //проверяем формат введенного значения ключа
    private boolean validateKey(Input input) {
        if (input.command.equals("1") || input.command.equals("2")) {
            try {
                Integer.parseInt(input.data[2]);
            } catch (NumberFormatException e) {
                System.out.println("Неправильный формат ключа!");
                return true;
            }
            if (Integer.parseInt(input.data[2]) > Constants.ALPHABET.length() || Integer.parseInt(input.data[2]) < 0) {
                System.out.println("Неправильный формат ключа!");
            }
        }
        return false;
    }
}
