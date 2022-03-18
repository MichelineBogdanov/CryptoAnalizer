package ru.javarush.bogdanov.cryptoanalizer.exeptions;

public class ValidateExeption extends RuntimeException{
    public ValidateExeption() {
    }

    public ValidateExeption(String message) {
        super(message);
    }

    public ValidateExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateExeption(Throwable cause) {
        super(cause);
    }
}
