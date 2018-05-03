package ihm;

import javax.swing.*;
import javax.swing.border.*;

import inscriptions.Inscriptions;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Interface extends JFrame  {

	JTextField lname = new JTextField();
	JTextField fname = new JTextField();
	JTextField mail = new JTextField();
	JLabel result = new JLabel();
	private JFrame frame = new JFrame();
	private Inscriptions inscriptions;
	
	public Interface(Inscriptions inscriptions) {
		this.inscriptions = inscriptions;
		initInterface();
	}
	
	public void initInterface() {
		frame.setTitle("Inscription Sportive Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new Accueil(frame, inscriptions));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}
	
	public static void close() {
		System.exit(0);
	}
}
