package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BruteForce implements Action {

    Decryptor decryptor = new Decryptor();

    @Override
    public Result execute(String[] datas) {
        //сохраняем данные из массива с данными в переменные
        String src = datas[0];
        String dest = datas[1];
        //проверять будем по трем условиям: 1 - n(а._А) == n(.) +/- 5%; 2 - n(а,_а) == n(,) +/- 5%; 3 - n(_) == n(слов) +/- 5%
        int[][] analys = new int[Constants.ALPHABET.length()][3];
        int[][] checkAnalysArray = new int[Constants.ALPHABET.length()][3];
        //прозодимся по всем ключам, используя тот код, что и для расшифровки
        for (int key = 0; key < Constants.ALPHABET.length(); key++) {
            //преобразовываем алфавит
            HashMap<Character, Character> mapa = makeMapa(key);
            //читаем из файла данные, преобразовываем, собираем статистику, какой ключ лучше всего подходит
            try (BufferedReader input = new BufferedReader(new FileReader(src), Constants.BUFFER_SIZE)) {
                char[] buffer = new char[Constants.BUFFER_SIZE];
                while (input.read(buffer) != -1) {
                    for (int i = 0; i < buffer.length; i++) {
                        if (mapa.containsKey(buffer[i])) {
                            buffer[i] = mapa.get(buffer[i]);
                        } else {
                            buffer[i] = buffer[i];
                        }
                    }
                    makeAnaliticForKey(analys, buffer, key);
                    checkAnalys(checkAnalysArray, buffer, key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] newData = new String[3];
        newData[0] = src;
        newData[1] = dest;
        newData[2] = String.valueOf(getKeyFromAnalys(analys, checkAnalysArray));
        return decryptor.execute(newData);
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

    //метод, собирающий аналитику данных текущего ключа
    private void makeAnaliticForKey(int[][] analysArray, char[] buffer, int key) {
        String str = new String(buffer);
        String[] regEx = new String[]{"[а-яa-z]. {1,}[-А-ЯA-Z\\[]", "[а-яa-z], {1,}[-а-яa-z\\[]", " {1,}"};
        for (int i = 0; i < regEx.length; i++) {
            Pattern pattern = Pattern.compile(regEx[i]);
            Matcher matcher = pattern.matcher(str);
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            analysArray[key][i] += count;
        }
    }

    private void checkAnalys(int[][] checkAnalysArray, char[] buffer, int key) {
        String str = new String(buffer);
        String[] regEx = new String[]{"\\.{1}", ",{1}"};
        for (int i = 0; i < regEx.length; i++) {
            Pattern pattern = Pattern.compile(regEx[i]);
            Matcher matcher = pattern.matcher(str);
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            checkAnalysArray[key][i] += count;
        }
        checkAnalysArray[key][2] += str.split(" {1,}").length;
    }

    //получаем ключ из анализа
    private int getKeyFromAnalys(int[][] analys, int[][] checkAnalysArray) {
        int[] result = new int[analys.length];
        int maxIndex = -1;
        for (int i = 0; i < analys.length; i++) {
            for (int k = 0; k < analys[i].length; k++) {
                if ((checkAnalysArray[i][k] > analys[i][k] * 0.5) && (checkAnalysArray[i][k] < analys[i][k] * 2)) {
                    result[i]++;
                }
            }
            if (result[i] == 3) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
