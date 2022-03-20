package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.*;
import java.util.HashMap;

public class Decryptor implements Action {

    @Override
    public Result execute(String[] datas) {
        //сохраняем данные из массива с данными в переменные
        String src = datas[0];
        String dest = datas[1];
        int key = Integer.parseInt(datas[2]);
        //преобразовываем алфавит
        HashMap<Character, Character> mapa = makeMapa(key);
        //читаем из файла данные, преобразовываем, записываем результат в файл
        try (BufferedReader input = new BufferedReader(new FileReader(src), Constants.BUFFER_SIZE);
             BufferedWriter output = new BufferedWriter(new FileWriter(dest), Constants.BUFFER_SIZE)) {
            char[] buffer = new char[Constants.BUFFER_SIZE];
            while (input.read(buffer) != -1) {
                for (int i = 0; i < buffer.length; i++) {
                    if (mapa.containsKey(buffer[i])) {
                        buffer[i] = mapa.get(buffer[i]);
                    } else {
                        buffer[i] = buffer[i];
                    }
                }
                output.write(buffer);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result("Операция выполнена!");
    }

    //составляем карту-преобразователь алфавита
    private HashMap<Character, Character> makeMapa(int key) {
        HashMap<Character, Character> result = new HashMap<>();
        char[] alphabetForMapa = Constants.ALPHABET.toCharArray();
        for (int i = 0; i < alphabetForMapa.length; i++) {
            Character keyForMapa = alphabetForMapa[(i + key) % alphabetForMapa.length];
            Character valueForMapa = alphabetForMapa[i];
            result.put(keyForMapa, valueForMapa);
        }
        return result;
    }
}
