package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.util.Scanner;

public class InteractiveConsoleRunner {
    public static void main(String[] args) {
        Application application = new Application();
        try {
            Result result = application.run(getParameters());
            System.out.println(result);
        } catch (ValidateExeption e) {
            //TODO обработать ошибку не найденного файла
        }
    }

    private static String[] getParameters() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение запущено, выберите действие (1 - Encrypt, 2 - Decrypt, 3 - Brute force, 4 - Static analys):");
        String choose = scanner.nextLine();
        if (choose.equals("1") || choose.equals("2")) {
            System.out.println("Введите через пробел путь к исходному файлу, выходному файлу и ключ:");
            String data = scanner.nextLine();
            String fullData = choose + data;
            return fullData.split(" ");
        } else if (choose.equals("3")) {
            System.out.println("Введите через пробел путь к исходному файлу и выходному файлу:");
            String data = scanner.nextLine();
            String fullData = choose + data;
            return fullData.split(" ");
        }
        return getParameters();
    }
}
