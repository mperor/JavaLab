package pl.mperor.lab.java;

import javax.swing.*;
import java.awt.*;

public class SwingForm {
    JFrame frame;
    JTextField input;
    JButton button;
    JLabel label;

    SwingForm() {
        frame = new JFrame("Basic Swing Form");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Enter text:");
        input = new JTextField(10);
        button = new JButton("Submit");
        label = new JLabel();

        frame.add(nameLabel);
        frame.add(input);
        frame.add(button);
        frame.add(label);

        button.addActionListener(e -> label.setText("Your text: %s!".formatted(input.getText())));

        frame.setSize(300, 100);
        frame.setVisible(true);
    }

}
