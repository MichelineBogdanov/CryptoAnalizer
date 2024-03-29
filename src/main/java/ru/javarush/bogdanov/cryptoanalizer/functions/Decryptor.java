package ru.javarush.bogdanov.cryptoanalizer.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateException;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.util.HashMap;

public class Decryptor implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(Decryptor.class);

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
            try {
                Character keyForMapa = alphabetForMapa[(i + key) % alphabetForMapa.length];
                Character valueForMapa = alphabetForMapa[i];
                result.put(keyForMapa, valueForMapa);
            } catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.error("ОШИБКА!!! {}", e.getClass());
                throw new ValidateException("Не получилось найти ключ(((");
            }
        }
        return result;
    }
}
