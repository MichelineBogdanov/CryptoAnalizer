package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateExeption;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.*;
import java.util.HashMap;

public class Encryptor implements Action {

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
            while (input.ready()) {
                int real = input.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    if (mapa.containsKey(buffer[i])) {
                        buffer[i] = mapa.get(buffer[i]);
                    } else {
                        buffer[i] = buffer[i];
                    }
                }
                output.write(buffer, 0, real);
            }
            output.flush();
        } catch (IOException e) {
            throw new ValidateExeption("Не удалось выполнить расшифровку(((");
        }
        return new Result("Операция выполнена!");
    }

    //составляем карту-преобразователь алфавита
    private HashMap<Character, Character> makeMapa(int key) {
        HashMap<Character, Character> result = new HashMap<>();
        char[] alphabetForMapa = Constants.ALPHABET.toCharArray();
        for (int i = 0; i < alphabetForMapa.length; i++) {
            Character keyForMapa = alphabetForMapa[i];
            Character valueForMapa = alphabetForMapa[(i + key) % alphabetForMapa.length];
            result.put(keyForMapa, valueForMapa);
        }
        return result;
    }

}
