package software.ulpgc.application;

import javax.swing.*;
import java.awt.*;

public class Desktop extends JFrame {
    public Desktop() {
        setTitle("Money Calculator");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel screen = new JPanel();
        screen.setLayout(new GridLayout(1, 2));

        createInputArea(screen);
        createOutputArea(screen);

        this.add(screen);
    }

    private void createOutputArea(JPanel screen) {
        JPanel panel = new JPanel();
        createTextField(panel, false);
        panel.add(new JButton("Clear"));
        screen.add(panel);
    }

    private void createInputArea(JPanel screen) {
        JPanel panel = new JPanel();
        createTextField(panel, true);
        panel.add(new JButton("Calculate"));
        screen.add(panel);
    }

    private void createTextField(JPanel panel, boolean editable) {
        JTextField textField = new JTextField(15);
        textField.setEditable(editable);
        panel.add(textField);
    }
}