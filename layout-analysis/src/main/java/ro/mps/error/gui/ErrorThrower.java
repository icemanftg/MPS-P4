package ro.mps.error.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ErrorThrower {

	public static void popErrorDialog(Component parent, String message){
		JOptionPane.showInternalMessageDialog(parent, message,
				"Layout Analyser Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
