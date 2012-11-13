/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author alexandra
 */
public class SmartTextBox extends Box {
    
	private JTextArea textArea;
	private ComponentResizer resizer;
	private ComponentMover mover;
	
    public SmartTextBox(int w, int h) {
        super(BoxLayout.Y_AXIS);

		Container cp = new Container();
		cp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel jl = new JLabel("\n");
		cp.add(jl);
		add(cp);
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(w, h-20));
		
		add(textArea);
		
		setPreferredSize(new Dimension(w, h));
		setBorder(BorderFactory.createLineBorder(Color.black));

		resizer = new ComponentResizer(this);
		mover = new ComponentMover(this, cp);
    }

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public ComponentResizer getResizer() {
		return resizer;
	}

	public void setResizer(ComponentResizer resizer) {
		this.resizer = resizer;
	}

	public ComponentMover getMover() {
		return mover;
	}

	public void setMover(ComponentMover mover) {
		this.mover = mover;
	}
	
	public String getText() {
		return textArea.getText();
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}

	public String[] getLines() {
		return textArea.getText().split("\n");
	}
	
	public String[] getWords() {
		return textArea.getText().split("\r\n\t ");
	}
}