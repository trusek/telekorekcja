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
    private JTextField bityPrzeklamaneField;
    private JTextField danePrzeklamaneField;
    private JPanel radioPanel;
    private JTextField komunikatField;
    private JTextField bitParzystosciField;
    private JLabel bityPrzeklamaneLabel;
    private JLabel daneBinarneLabel;
    private JLabel danePrzeklamaneLabel;
    private JLabel daneTekstoweLabel;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private int radioOption = 1;
    private String crc12 = "1100000001111";
    private String crc16 = "11000000000000101";
    private String crcItu = "10001000000100001";
    private String atm = "100000111";

    public MainForm() {

        button1.addActionListener(e -> {
            switch (radioOption) {
                case 1:
                    parity();
                    break;
                case 2:
                    crcEncode(crc16);
                    crcDecode(crc16);
                    break;
                case 3:
                    crcEncode(crc12);
                    crcDecode(crc12);
                    break;
                case 4:
//                    hamming();
                    hammingAction();
                    break;
                case 5:
                    crcEncode(crcItu);
                    crcDecode(crcItu);
                    break;
                case 6:
                    crcEncode(atm);
                    crcDecode(atm);
                    break;
            }
        });
        radioButton1.addActionListener(e -> {
            radioOption = 1;
            customFieldNames();
        });
        radioButton2.addActionListener(e -> {
            radioOption = 2;
            crcFieldNames();
        });
        radioButton3.addActionListener(e -> {
            radioOption = 3;
            crcFieldNames();
        });
        radioButton5.addActionListener(e -> {
            radioOption = 5;
            crcFieldNames();
        });
        radioButton6.addActionListener(e -> {
            radioOption = 6;
            crcFieldNames();
        });
        radioButton4.addActionListener(e -> {
            radioOption = 4;
            hammingFieldNames();
        });
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

    public void parity() {
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
            if (tab[i] != '0' && tab[i] != '1') {
                error = "Niepoprawne dane wejściowe";
                komunikatField.setText(error);
            }
        }

        //dodanie bitu parzystości
        if (parity % 2 == 0) {
            endString += 0;
            bitParzystosciField.setText("0");
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
            if (tab2[i] != '0' && tab2[i] != '1') {
                error = "Niepoprawne dane wyjściowe";
                komunikatField.setText(error);
            }
        }

        //sprawdzenie czy nie ma błędów przekłamanym ciągu
        if (error == null) {
            if (parity2 % 2 == 1)
                komunikatField.setText("Wystąpił błąd");
            else
                komunikatField.setText("Nie znaleziono błędu");
        }
    }

    //obliczenie sumy kontrolnej i dodanie jej do wiadomości
    public void crcEncode(String generator) {
        String message = daneBinarneField.getText();
        StringBuilder encoded = new StringBuilder(message);
        for (int i = 0; i < generator.length() - 1; i++) {
            encoded.append('0');
        }
        encodeHelper(generator, encoded);
        String finalMessage = message + encoded.toString().substring(encoded.length() - generator.length() + 1);
        bityPrzeklamaneField.setText(finalMessage);
    }

    private void encodeHelper(String generator, StringBuilder encoded) {
        for (int i = 0; i <= encoded.length() - generator.length(); ) {
            for (int j = 0; j < generator.length(); j++) {
                //operacja modulo
                if (encoded.charAt(i + j) == generator.charAt(j)) {
                    encoded.setCharAt(i + j, '0');
                } else {
                    encoded.setCharAt(i + j, '1');
                }
            }
            for (; i < encoded.length() && encoded.charAt(i) != '1'; i++) ;
        }
    }

    //sprawdzenie poprawności wiadomości odebranej
    public void crcDecode(String generator) {
        String message = danePrzeklamaneField.getText();
        if (message != null && !message.isEmpty()) {
            boolean error = false;
            StringBuilder encoded = new StringBuilder(message);
            encodeHelper(generator, encoded);
            for (int i = encoded.length() - generator.length(); i < encoded.length(); i++) {
                //jeśli po wykonaniu operacji modulo (na wiadomości odebranej) reszta jest różna od 0 wystąpił błąd
                if (encoded.charAt(i) != '0') {
                    error = true;
                }
            }
            if (error) {
                komunikatField.setText("Błąd w komunikacji");
            } else {
                komunikatField.setText("Wiadomość poprawna");
            }
        }
    }

    public void crcFieldNames() {
        daneTekstoweLabel.setText("Wiadomość tekstowa");
        daneBinarneLabel.setText("Wiadomość binarna");
        bityPrzeklamaneLabel.setText("Wiadomość wysłana");
        danePrzeklamaneLabel.setText("Wiadomość odebrana");
    }

    public void customFieldNames() {
        daneTekstoweLabel.setText("Dane tekstowe");
        daneBinarneLabel.setText("Dane binarne");
        bityPrzeklamaneLabel.setText("Bity przekłamane");
        danePrzeklamaneLabel.setText("Dane po przekłamaniu");
    }

    public void hammingFieldNames() {
        daneTekstoweLabel.setText("Wiadomość tekstowa");
        daneBinarneLabel.setText("Dane binarne");
        bityPrzeklamaneLabel.setText("Ilość wymaganych bitów parzystośći");
        danePrzeklamaneLabel.setText("Zakodowana wiadomość");
    }


    public void hammingAction() {
        String msg = daneBinarneField.getText();
        int r = 0;
        int m = msg.length();
        //calculate number of parity bits needed using m+r+1<=2^r
        while (true) {
            if (m + r + 1 <= Math.pow(2, r)) {
                break;
            }
            r++;
        }
//        System.out.println("Number of parity bits needed : " + r);
        bityPrzeklamaneField.setText("" + r);
        int transLength = msg.length() + r, temp = 0, temp2 = 0, j = 0;
        int transMsg[] = new int[transLength + 1]; //+1 because starts with 1
        for (int i = 1; i <= transLength; i++) {
            temp2 = (int) Math.pow(2, temp);
            if (i % temp2 != 0) {
                transMsg[i] = Integer.parseInt(Character.toString(msg.charAt(j)));
                j++;
            } else {
                temp++;
            }
        }
//        for (int i = 1; i <= transLength; i++) {
//            System.out.print(transMsg[i]);
//        }
//        System.out.println();

        for (int i = 0; i < r; i++) {
            int smallStep = (int) Math.pow(2, i);
            int bigStep = smallStep * 2;
            int start = smallStep, checkPos = start;
//            System.out.println("Calculating Parity bit for Position : " + smallStep);
//            System.out.print("Bits to be checked : ");
            while (true) {
                for (int k = start; k <= start + smallStep - 1; k++) {
                    checkPos = k;
//                    System.out.print(checkPos + " ");
                    if (k > transLength) {
                        break;
                    }
                    transMsg[smallStep] ^= transMsg[checkPos];
                }
                if (checkPos > transLength) {
                    break;
                } else {
                    start = start + bigStep;
                }
            }
//            System.out.println();
        }
        StringBuilder hammingMessage = new StringBuilder();
        //Display encoded message
//        System.out.print("Hamming Encoded Message : ");
        for (int i = 1; i <= transLength; i++) {
            hammingMessage.append(transMsg[i]);
//            System.out.print(transMsg[i]);
        }
        danePrzeklamaneField.setText(hammingMessage.toString());
//        System.out.print(hammingMessage);
//        System.out.println();
    }
}
