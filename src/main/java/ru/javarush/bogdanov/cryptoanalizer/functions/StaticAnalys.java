package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateException;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StaticAnalys implements Action {

    @Override
    public Result execute(String[] datas) {
        //сохраняем данные из массива с данными в переменные
        String src = datas[0];
        String dest = datas[1];
        String dictionary = datas[2];
        //анализируем исходник и словарь
        HashMap<Character, Integer> analiseMapFromSrc = textAnalise(src);
        HashMap<Character, Integer> analiseMapFromDictionary = textAnalise(dictionary);
        //сортируем полученные данные
        ArrayList<Character> sortesSrc = sortMapa(analiseMapFromSrc);
        ArrayList<Character> sortesDictionary = sortMapa(analiseMapFromDictionary);
        //получаем словарь для преобразования текста
        HashMap<Character, Character> textDictionary = makeCharMapa(sortesSrc, sortesDictionary);
        //читаем из файла данные, преобразовываем, записываем результат в файл
        readWriteToFile(src, dest, textDictionary);
        return new Result("Операция выполнена!");
    }

    //анализируем словарь по частоте встречающихся символов
    private HashMap<Character, Integer> textAnalise(String pathToFile) {
        HashMap<Character, Integer> mapa = new HashMap<>();
        //заносим в мапу словарь символов
        for (int i = 0; i < Constants.ALPHABET.length(); i++) {
            mapa.put(Constants.ALPHABET.toCharArray()[i], 0);
        }
        //читаем из файла данные, считаем частоту встречающихся символов, заносим в мапу
        try (BufferedReader input = new BufferedReader(new FileReader(pathToFile), Constants.BUFFER_SIZE)) {
            char[] buffer = new char[Constants.BUFFER_SIZE];
            while (input.read(buffer) != -1) {
                for (char c : buffer) {
                    if (mapa.containsKey(c)) {
                        mapa.put(c, mapa.get(c) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    //сортируем полученные мапы (!!!данные кусок кода был бессовестно украден из интернета, в лучших традициях программистов XDDD!!!)
    //на самом деле решение изящное, поэтому решил его оставить
    private ArrayList<Character> sortMapa(HashMap<Character, Integer> mapa) {
        Map<Character, Integer> sortedMap = mapa.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new ValidateException("Не удалось отсортировать словарь/исходный текст");
                        },
                        LinkedHashMap::new
                ));
        //далее уже было написано мной
        ArrayList<Character> sortedArrayList = new ArrayList<>();
        for (Map.Entry<Character, Integer> characterIntegerEntry : sortedMap.entrySet()) {
            sortedArrayList.add(characterIntegerEntry.getKey());
        }
        return sortedArrayList;

    }

    //составляем из двух линкед мап словарь для перевода текста
    private HashMap<Character, Character> makeCharMapa(ArrayList<Character> mapaSrc, ArrayList<Character> mapaDictionary) {
        HashMap<Character, Character> result = new HashMap<>();
        for (int i = 0; i < mapaDictionary.size(); i++) {
            result.put(mapaSrc.get(i), mapaDictionary.get(i));
        }
        return result;
    }


}
