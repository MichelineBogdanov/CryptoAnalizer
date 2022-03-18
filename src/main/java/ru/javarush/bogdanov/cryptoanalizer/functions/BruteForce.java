package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class BruteForce implements Action {

    private Result result;
    String[] datas;

    public BruteForce(String[] datas) {
        this.datas = datas;
    }

    @Override
    public Result execute() {
        return null;
    }

}
