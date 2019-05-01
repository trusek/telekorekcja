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
                    parity();
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
    public void parity(){
        String error = null;
        String endString = "";

        String oryginalString = daneBinarneField.getText();
        String faultString = bityPrzeklamaneField.getText();

        char[] tab = oryginalString.toCharArray();
        char[] tab2 = faultString.toCharArray();

        int parity = 0;
        int parity2 = 0;

        //walidacja i ustalenie porzystości pierwszego ciągu
        for (int i = 0; i < oryginalString.length(); i++) {
            if (tab[i] == '1') {
                parity++;
            }
            if (tab[i] == '0') {
                continue;
            }
            if(tab[i] != '0' && tab[i] != '1'){
                error = "Niepoprawne dane wejściowe";
                komunikatField.setText(error);
            }
        }

        //dodanie bitu parzystości
        if (parity % 2 == 0) {
            endString += 0;
            bitParzystosciField.setText("1");
        } else if (parity % 2 == 1) {
            endString += 1;
            bitParzystosciField.setText("1");
        }

        //stworzenie strigu wyjściowego
        for (int i = 0; i < oryginalString.length(); i++) {
            endString += tab[i];
        }
        danePrzeklamaneField.setText(endString);

        //walidacja drugiego ciągu
        if (faultString.length() != endString.length()) {
            error = "Niepoprawna długość ciągu";
            komunikatField.setText(error);
        }
        for (int i = 0; i < faultString.length(); i++) {
            if (tab2[i] == '1') {
                parity2++;
            }
            if (tab2[i] == '0') {
                continue;
            }
            if(tab2[i] != '0' && tab2[i] != '1'){
                error = "Niepoprawne dane wyjściowe";
                komunikatField.setText(error);
            }
        }

        //sprawdzenie czy nie ma błędów przekłamanym ciągu
        if(error == null)
        {
            if(parity2 % 2 == 1)
                komunikatField.setText("Wystąpił błąd");
            else
                komunikatField.setText("Nie znaleziono błędu");
        }
    }

}
