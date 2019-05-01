package com.apw;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainForm {

    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton button1;
    private JPanel mainPanel;
    private JTextField daneTekstoweField;
    private JTextField daneBinarneField;
    private JTextField daneAlgorytmuField;
    private JTextField bityPrzeklamaneField;
    private JTextField danePrzeklamaneField;
    private JPanel radioPanel;
    private JTextField komunikatField;
    private JTextField bitParzystosciField;
    private int radioOption = 1;

    public MainForm() {

        button1.addActionListener(e -> {
            switch (radioOption) {
                case 1:
                    doAction(1);

                    //todo dodać obsługę gdy jest wybrany 1 radio
                    // jakąć funkcje żeby ładnie się patrzyło i zmieniało ten kod
                    break;
                case 2:
                    doAction(2);
                    //todo dodać obsługę gdy jest wybrany 2 radio
                    break;
                case 3:
                    doAction(1);
                    //todo dodać obsługę gdy jest wybrany 3 radio
                    break;
                case 4:
                    doAction(1);
                    //todo dodać obsługę gdy jest wybrany 4 radio
                    break;
            }
        });
        radioButton1.addActionListener(e -> radioOption = 1);
        radioButton2.addActionListener(e -> radioOption = 2);
        radioButton3.addActionListener(e -> radioOption = 3);
        radioButton4.addActionListener(e -> radioOption = 4);
        daneTekstoweField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                daneBinarneField.setText(stringToBinary(daneTekstoweField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                daneBinarneField.setText(stringToBinary(daneTekstoweField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                daneBinarneField.setText(stringToBinary(daneTekstoweField.getText()));
            }
        });
    }

    private void doAction(int a) {
        daneTekstoweField.setText("dupa dupa" + a);

    }

    public String stringToBinary(String str) {

        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append("");
        }

        return binary.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Telekorekcje");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
