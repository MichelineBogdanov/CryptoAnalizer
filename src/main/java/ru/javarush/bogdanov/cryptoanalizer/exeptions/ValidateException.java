package ru.javarush.bogdanov.cryptoanalizer.exeptions;

public class ValidateException extends RuntimeException{
    public ValidateException(String message) {
        super(message);
    }
}
