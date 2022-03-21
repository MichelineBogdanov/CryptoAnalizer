package ru.javarush.bogdanov.cryptoanalizer.iodata;

public class Input {
    public String command;
    public String[] data;

    public Input(String command, String[] data) {
        this.command = command;
        this.data = data;
    }

    public Input(String command) {
        this.command = command;
    }
}
