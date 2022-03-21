package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.IOException;
import java.util.Scanner;

public class InteractiveConsoleRunner {
    public static void main(String[] args) {
        Application application = new Application();
        try {
            Result result = application.run(getParameters());
            System.out.println(result);
        } catch (ValidateExeption | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //C:\Users\qqMik\Desktop\src.txt C:\Users\qqMik\Desktop\dest.txt 4

    private static Input getParameters() {
        Validator validator = new Validator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение запущено!");
        while (true) {
            System.out.println("Выберите действие (1 - Encrypt, 2 - Decrypt, 3 - Brute force, 4 - Static analys, 5 - Выход):");
            String choose = scanner.nextLine();
            String data;
            Input result;
            switch (choose) {
                case "1":
                case "2": {
                    do {
                        System.out.println("Введите через пробел путь к исходному файлу, выходному файлу и ключ (от 1 до " + Constants.ALPHABET.length() + "):");
                        data = scanner.nextLine();
                        result = new Input(choose, data.split(" "));
                    } while (validator.validateSrc(result) || validator.validateDest(result) || validator.validateKey(result));
                    return result;
                }
                case "3":
                    do {
                        System.out.println("Введите через пробел путь к исходному файлу и выходному файлу:");
                        data = scanner.nextLine();
                        result = new Input(choose, data.split(" "));
                    } while (validator.validateSrc(result) || validator.validateDest(result));
                    return result;
                case "4": {
                    System.out.println("Введите через пробел путь к исходному файлу, выходному файлу и словарю:");
                    data = scanner.nextLine();
                    do {
                        result = new Input(choose, data.split(" "));
                    } while (validator.validateSrc(result) || validator.validateDest(result));
                    return result;
                }
                case "5":
                    return new Input(choose);
                default: {
                    System.out.println("Неизвестная команда, попробуйте еще раз");
                }
            }
        }
    }
}
