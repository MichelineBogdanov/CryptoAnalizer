package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

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
        readWriteToFile(src, dest, mapa);
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
