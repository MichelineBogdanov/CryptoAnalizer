package ru.javarush.bogdanov.cryptoanalizer.iodata;

public class Result {

    String exitCode;

    public Result(String exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "exitCode='" + exitCode + '\'' +
                '}';
    }
}
