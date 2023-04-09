package ru.javarush.bogdanov.cryptoanalizer.view;

import javax.swing.*;
import java.awt.*;

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

    public SwingForm() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        this.setBounds(width / 4, height / 4, width / 2, height / 2);

        String[] operations = {"Encrypt", "Decrypt", "BruteForce", "Analyse"};
        selectOperation.setModel(new DefaultComboBoxModel<>(operations));

        srcTextArea.setLineWrap(true);
        //JScrollPane srcScroll = new JScrollPane(srcTextArea);
        //srcTextArea.setEditable(false);
        //srcScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//
        destTextArea.setLineWrap(true);
        //JScrollPane destScroll = new JScrollPane(destTextArea);
        //destTextArea.setEditable(false);
        //destScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
