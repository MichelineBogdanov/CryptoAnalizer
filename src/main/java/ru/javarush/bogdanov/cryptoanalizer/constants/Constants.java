package ru.javarush.bogdanov.cryptoanalizer.constants;

import java.util.Locale;

public class Constants {
    private static final String RU_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String CYPHERS = "0123456789";
    private static final String CHARS = ".,”’:;-!? ";

    public static final int BUFFER_SIZE = 2000;
    public static final String ALPHABET = RU_ALPHABET + RU_ALPHABET.toLowerCase(Locale.ROOT) + CYPHERS + CHARS;

}
