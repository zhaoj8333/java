package lang.java8.functional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingTest {
    public static void main(String[] args) {
        JFrame custom_frame = new JFrame("Custom Frame");
        JButton button = new JButton("Button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button pressed");
            }
        });
        custom_frame.add(button);
        custom_frame.pack();
        custom_frame.setVisible(true);
        custom_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
