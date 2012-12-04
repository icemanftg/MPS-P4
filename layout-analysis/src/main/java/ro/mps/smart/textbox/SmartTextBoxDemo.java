/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.mps.smart.textbox;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Alexandra
 */
public class SmartTextBoxDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart TextBox Demo");
        final SmartTextBox box = new SmartTextBox(200, 300);
        JButton buton = new JButton("Clear text");
        
        buton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                box.setText("");
            }
        });
        
        box.setText("Hey there!");
        
        frame.add(box);
        frame.add(buton);
        
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}
