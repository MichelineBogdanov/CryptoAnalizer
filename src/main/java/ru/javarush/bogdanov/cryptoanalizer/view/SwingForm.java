package ru.javarush.bogdanov.cryptoanalizer.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.javarush.bogdanov.cryptoanalizer.Application;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Input;
import ru.javarush.bogdanov.cryptoanalizer.iodata.Result;

import javax.swing.*;
import java.awt.*;
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

        //this.getContentPane().add(mainPanel);

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

        this.add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadDictButton.setEnabled(false);
        dictTextField.setEnabled(false);

        loadSrcButton.addActionListener(e -> chooseFile(srcTextField, srcTextArea));

        loadDestButton.addActionListener(e -> chooseFile(destTextField, destTextArea));

        saveSrcButton.addActionListener(e -> saveFile(srcTextField, srcTextArea));

        saveDestButton.addActionListener(e -> saveFile(destTextField, destTextArea));

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
                default -> data = new String[0];
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

    /*public JPanel getMainPanel() {
        return mainPanel;
    }*/

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        south = new JPanel();
        south.setLayout(new GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(south, BorderLayout.SOUTH);
        operation = new JLabel();
        operation.setText("Operation");
        south.add(operation, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executeButton = new JButton();
        executeButton.setText("Execute");
        south.add(executeButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        source = new JLabel();
        source.setText("Source");
        south.add(source, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        destination = new JLabel();
        destination.setText("Destination");
        south.add(destination, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dictionary = new JLabel();
        dictionary.setText("Dictionary");
        south.add(dictionary, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadSrcButton = new JButton();
        loadSrcButton.setText("LoadSrc");
        south.add(loadSrcButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveSrcButton = new JButton();
        saveSrcButton.setText("SaveSrc");
        south.add(saveSrcButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectOperation = new JComboBox();
        south.add(selectOperation, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadDestButton = new JButton();
        loadDestButton.setText("LoadDest");
        south.add(loadDestButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveDestButton = new JButton();
        saveDestButton.setText("SaveDest");
        south.add(saveDestButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        srcTextField = new JTextField();
        south.add(srcTextField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        destTextField = new JTextField();
        south.add(destTextField, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dictTextField = new JTextField();
        south.add(dictTextField, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        key = new JLabel();
        key.setText("Key");
        south.add(key, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyTextField = new JTextField();
        south.add(keyTextField, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loadDictButton = new JButton();
        loadDictButton.setText("LoadDict");
        south.add(loadDictButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        swapSrcDest = new JButton();
        swapSrcDest.setText("SwapSrcDest");
        swapSrcDest.setMnemonic('D');
        swapSrcDest.setDisplayedMnemonicIndex(7);
        south.add(swapSrcDest, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultLetter = new JTextField();
        south.add(resultLetter, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        swapLetters = new JButton();
        swapLetters.setText("SwapLetters");
        south.add(swapLetters, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetLetter = new JTextField();
        south.add(targetLetter, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        infoAbSwap = new JLabel();
        infoAbSwap.setText("Swap letters in dest area");
        south.add(infoAbSwap, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        center = new JPanel();
        center.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(center, BorderLayout.CENTER);
        srcLabel = new JLabel();
        srcLabel.setText("scr");
        center.add(srcLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        destLabel = new JLabel();
        destLabel.setText("dest");
        center.add(destLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        center.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        srcTextArea = new JTextArea();
        scrollPane1.setViewportView(srcTextArea);
        final JScrollPane scrollPane2 = new JScrollPane();
        center.add(scrollPane2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        destTextArea = new JTextArea();
        scrollPane2.setViewportView(destTextArea);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
