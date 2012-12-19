/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.mps.smart.textbox;

import ro.mps.smart.frame.SmartFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexandra
 */
public class SmartTextBoxDemo {
    public static void main(String[] args) {
        SmartFrame frame = new SmartFrame("Smart TextBox Demo");
        final SmartTextBox box = new SmartTextBox(200, 300);
        JButton buton = new JButton("Clear text");

        buton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                box.setText("");
            }
        });

        String z = "";
        for (int i = 0; i < 10; ++i) {
            z += "line " + i + "\n";
        }

//        box.setBounds(100, 200, 200, 300);

        frame.add(box);
        frame.add(buton);

        box.setText(z);

        frame.getFrame().setSize(800, 600);
        frame.getFrame().setVisible(true);
    }
}
