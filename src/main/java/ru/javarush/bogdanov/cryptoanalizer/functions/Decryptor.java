package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import java.nio.file.Files;

public class Decryptor implements Action {

    private Result result;
    String[] datas;

    public Decryptor(String[] datas) {
        this.datas = datas;
    }

    @Override
    public Result execute() {
        return null;
    }

}
