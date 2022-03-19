package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.constants.Constants;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.io.*;
import java.util.HashMap;

public class Decryptor implements Action {

    @Override
    public Result execute(String[] datas) {
        String src = datas[0];
        String dest = datas[1];
        int key = Integer.parseInt(datas[2]);
        HashMap<Character, Character> mapa = makeMapa(key);
        try (BufferedReader input = new BufferedReader(new FileReader(src), 10); BufferedWriter output = new BufferedWriter(new FileWriter(dest), 10)) {
            char[] inputBuffer = new char[10];
            char[] outputBuffer = new char[10];
            while (input.read(inputBuffer) != -1) {
                for (int i = 0; i < inputBuffer.length; i++) {
                    if (mapa.containsKey(inputBuffer[i])) {
                        outputBuffer[i] = mapa.get(inputBuffer[i]);
                    } else {
                        outputBuffer[i] = inputBuffer[i];
                    }
                }
                output.write(outputBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
