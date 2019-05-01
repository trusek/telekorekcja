package com.apw;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {

    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton button1;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private int radioOption = 1;

    public MainForm() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioOption = 1;
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioOption = 2;
            }
        });
        radioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioOption = 3;
            }
        });
        radioButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioOption = 4;
            }
        });
    }

    private void doAction(int a) {
        textField1.setText("dupa dupa" + a);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Telekorekcje");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
