package ru.javarush.bogdanov.cryptoanalizer.functions;

import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

public class Exit implements Action {

    @Override
    public Result execute(String[] datas) {
        return new Result("Выход! До скорых встреч!");
    }

}
