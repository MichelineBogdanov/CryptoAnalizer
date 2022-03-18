package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.nio.file.Files;
import java.nio.file.Path;

public class Encryptor implements Action {

    private Result result;
    String[] datas;

    public Encryptor(String[] datas) {
        this.datas = datas;
    }

    @Override
    public Result execute() {
        return null;
    }

}
