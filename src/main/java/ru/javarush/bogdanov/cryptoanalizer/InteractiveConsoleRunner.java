package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
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
            e.printStackTrace();
        }
    }

    //C:\Users\qqMik\Desktop\src.txt C:\Users\qqMik\Desktop\dest.txt 4

    private static String[] getParameters() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение запущено!");
        while (true) {
            System.out.println("Выберите действие (1 - Encrypt, 2 - Decrypt, 3 - Brute force, 4 - Static analys, 5 - Выход):");
            String choose = scanner.nextLine();
            switch (choose) {
                case "1":
                case "2": {
                    System.out.println("Введите через пробел путь к исходному файлу, выходному файлу и ключ:");
                    String data = scanner.nextLine();
                    String fullData = String.join(" ", choose, data);
                    return fullData.split(" ");
                }
                case "3":
                case "4": {
                    System.out.println("Введите через пробел путь к исходному файлу, выходному файлу и словарю (при наличии для brute force):");
                    String data = scanner.nextLine();
                    String fullData = String.join(" ", choose, data);
                    return fullData.split(" ");
                }
                case "5":
                    return new String[]{"5"};
                default: {
                    System.out.println("Неизвестная команда, попробуйте еще раз");
                }
            }
        }
    }
}
