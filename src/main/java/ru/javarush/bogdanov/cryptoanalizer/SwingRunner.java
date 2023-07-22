package ru.javarush.bogdanov.cryptoanalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javarush.bogdanov.cryptoanalizer.view.SwingForm;

public class SwingRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwingRunner.class);

    public static void main(String[] args) {
        LOGGER.info("Старт приложения");
        SwingForm jFrame = new SwingForm();
        jFrame.setVisible(true);
    }

}
