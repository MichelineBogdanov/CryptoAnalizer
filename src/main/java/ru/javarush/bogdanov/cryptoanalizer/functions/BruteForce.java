package ru.javarush.bogdanov.cryptoanalizer.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.exeptions.ValidateException;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BruteForce implements Action {

    Decryptor decryptor;

    public static final Logger LOGGER = LoggerFactory.getLogger(BruteForce.class);

    public BruteForce() {
        this.decryptor = new Decryptor();
    }

    @Override
    public Result execute(String[] datas) {
        //сохраняем данные из массива с данными в переменные
        String src = datas[0];
        String dest = datas[1];
        //проверять будем по трем условиям: 1 - максимум n(а._А); 2 - максимум n(а,_а); 3 - максимум n(_)
        int[][] analys = new int[Constants.ALPHABET.length()][3];
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
                        }
                    }
                    makeAnaliticForKey(analys, buffer, key);
                }
            } catch (IOException e) {
                ValidateException validateException = new ValidateException("Не удалось выполнить Brute force(((");
                LOGGER.error("ОШИБКА!!!/n {}/n TRACE:/n {}/n", validateException.getClass(), validateException.getStackTrace());
                throw validateException;
            }
        }
        String[] newData = new String[3];
        newData[0] = src;
        newData[1] = dest;
        newData[2] = String.valueOf(getKeyFromAnalys(analys));
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
        String[] regEx = new String[]{"[а-яa-z][!?.] {1,}[-А-ЯA-Z]", "[а-яa-z], {1,}[-а-яa-z]", " {1,}"};
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

    //получаем ключ из анализа
    private int getKeyFromAnalys(int[][] analys) {
        int[] resultValue = new int[]{-1, -1, -1};
        int[] resultIndex = new int[]{-1, -1, -1};
        for (int i = 0; i < analys.length; i++) {
            for (int k = 0; k < analys[i].length; k++) {
                if (analys[i][k] > resultValue[k]) {
                    resultValue[k] = analys[i][k];
                    resultIndex[k] = i;
                }
            }
        }
        if (resultIndex[0] == resultIndex[1] && resultIndex[0] == resultIndex[2]) {
            LOGGER.info("Удалось найти ключ {}", resultIndex[0]);
            return resultIndex[0];
        }
        return -1;
    }
}
