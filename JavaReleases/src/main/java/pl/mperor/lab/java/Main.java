package pl.mperor.lab.java;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Java key features module!");
        SwingUtilities.invokeLater(SwingForm::new);
    }

}