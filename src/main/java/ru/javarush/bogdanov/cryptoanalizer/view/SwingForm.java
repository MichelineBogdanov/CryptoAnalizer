package ru.javarush.bogdanov.cryptoanalizer.view;

import ru.javarush.bogdanov.cryptoanalizer.Application;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class SwingForm extends JFrame {
    private JPanel mainPanel;
    private JPanel center;
    private JPanel south;
    private JTextArea srcTextArea;
    private JTextArea destTextArea;
    private JComboBox<String> selectOperation;
    private JButton executeButton;
    private JButton loadSrcButton;
    private JButton saveSrcButton;
    private JButton loadDestButton;
    private JButton saveDestButton;
    private JButton loadDictButton;
    private JTextField srcTextField;
    private JTextField destTextField;
    private JTextField dictTextField;
    private JTextField keyTextField;
    private JLabel operation;
    private JLabel source;
    private JLabel destination;
    private JLabel dictionary;
    private JLabel key;
    private JLabel srcLabel;
    private JLabel destLabel;
    private JTextField targetLetter;
    private JButton swapLetters;
    private JTextField resultLetter;
    private JButton swapSrcDest;
    private JLabel infoAbSwap;

    Application app = new Application();

    JFileChooser fileChooser = new JFileChooser();

    public SwingForm() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        this.setBounds(width / 4, height / 4, width / 2, height / 2);

        srcTextArea.setLineWrap(true);
        srcTextArea.setWrapStyleWord(true);
        destTextArea.setLineWrap(true);
        destTextArea.setWrapStyleWord(true);

        String[] operations = {"Encrypt", "Decrypt", "BruteForce", "Analyse"};
        selectOperation.setModel(new DefaultComboBoxModel<>(operations));

        loadDictButton.setEnabled(false);
        dictTextField.setEnabled(false);

        this.add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadSrcButton.addActionListener(e -> {
            chooseFile(srcTextField, srcTextArea);
        });

        loadDestButton.addActionListener(e -> {
            chooseFile(destTextField, destTextArea);
        });

        saveSrcButton.addActionListener(e -> {
            saveFile(srcTextField, srcTextArea);
        });

        saveDestButton.addActionListener(e -> {
            saveFile(destTextField, destTextArea);
        });

        //загрузка словаря
        loadDictButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String absolutePath = selectedFile.getAbsolutePath();
                dictTextField.setText(absolutePath);
            }
        });

        executeButton.addActionListener(e -> {
            String command = (String) selectOperation.getSelectedItem();
            String[] data;
            switch (Objects.requireNonNull(command)) {
                case "Encrypt", "Decrypt" -> {
                    data = new String[3];
                    data[0] = srcTextField.getText();
                    data[1] = destTextField.getText();
                    data[2] = keyTextField.getText();
                }
                case "BruteForce" -> {
                    data = new String[2];
                    data[0] = srcTextField.getText();
                    data[1] = destTextField.getText();
                }
                case "Analyse" -> {
                    data = new String[3];
                    data[0] = srcTextField.getText();
                    data[1] = destTextField.getText();
                    data[2] = dictTextField.getText();
                }
                default -> {
                    data = new String[0];
                }
            }
            Input in = new Input(command, data);
            Result run = app.run(in);
            String path = destTextField.getText();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                destTextArea.setText(builder.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, run.toString(), "Результат", JOptionPane.INFORMATION_MESSAGE);
        });

        swapSrcDest.addActionListener(e -> {
            String srcText = srcTextArea.getText();
            String destText = destTextArea.getText();
            srcTextArea.setText(destText);
            destTextArea.setText(srcText);
        });

        selectOperation.addItemListener(e -> {
            if (Objects.equals(selectOperation.getSelectedItem(), operations[0])
                    || Objects.equals(selectOperation.getSelectedItem(), operations[1])) {
                loadDictButton.setEnabled(false);
                dictTextField.setEnabled(false);
                keyTextField.setEnabled(true);
            } else if (Objects.equals(selectOperation.getSelectedItem(), operations[2])) {
                loadDictButton.setEnabled(false);
                dictTextField.setEnabled(false);
                keyTextField.setEnabled(false);
            } else if (Objects.equals(selectOperation.getSelectedItem(), operations[3])) {
                loadDictButton.setEnabled(true);
                dictTextField.setEnabled(true);
                keyTextField.setEnabled(false);
            }
        });

        swapLetters.addActionListener(e -> {
            String destText = destTextArea.getText();
            char[] destCharArray = destText.toCharArray();
            char targetChar = targetLetter.getText().charAt(0);
            char resultChar = resultLetter.getText().charAt(0);

            for (int i = 0; i < destCharArray.length; i++) {
                char c = destCharArray[i];
                if (targetChar == c) {
                    destCharArray[i] = resultChar;
                } else if (resultChar == c) {
                    destCharArray[i] = targetChar;
                }
            }
            String replace = new String(destCharArray);
            destTextArea.setText(replace);
        });
    }

    private void chooseFile(JTextField textField, JTextArea textArea) {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String absolutePath = selectedFile.getAbsolutePath();
            textField.setText(absolutePath);
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                textArea.setText(builder.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void saveFile(JTextField textField, JTextArea textArea) {
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String absolutePath = selectedFile.getAbsolutePath();
            textField.setText(absolutePath);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {
                String text = textArea.getText();
                bufferedWriter.write(text);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void swapLetters(JTextArea textArea) {

    }

}
