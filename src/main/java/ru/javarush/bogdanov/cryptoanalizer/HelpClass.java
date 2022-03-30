package ru.javarush.bogdanov.cryptoanalizer;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateException;

import java.io.*;
import java.util.HashMap;

public class HelpClass {

    public void readWriteToFile(String src, String dest, HashMap<Character, Character> mapa) {
        try (BufferedReader input = new BufferedReader(new FileReader(src), Constants.BUFFER_SIZE);
             BufferedWriter output = new BufferedWriter(new FileWriter(dest), Constants.BUFFER_SIZE)) {
            char[] buffer = new char[Constants.BUFFER_SIZE];
            while (input.ready()) {
                int real = input.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    if (mapa.containsKey(buffer[i])) {
                        buffer[i] = mapa.get(buffer[i]);
                    }
                }
                output.write(buffer, 0, real);
            }
            output.flush();
        } catch (IOException e) {
            throw new ValidateException("Не удалось выполнить шифрование(((");
        }
    }
}
