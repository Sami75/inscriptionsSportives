package ihm;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Interface extends JFrame  {

	JTextField lname = new JTextField();
	JTextField fname = new JTextField();
	JTextField mail = new JTextField();
	JLabel result = new JLabel();
	private JFrame frame = new JFrame();
	
	public Interface() {
		initInterface();
	}
	
	public void initInterface() {
		frame.setTitle("Inscription Sportive Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new Accueil(frame));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}
	
	public static void close() {
		System.exit(0);
	}

	
	
	
//	
//	private JPanel CompetitionIhm() {
//		JPanel compet = new JPanel();
//		compet.setLayout(new FlowLayout());
//		ArrayList<JButton> jButtons = new ArrayList<>();
//		jButtons.add(new JButton("Créer une compétition"));
//		jButtons.add(new JButton("Lister les compétitions"));
//		jButtons.add(new JButton("Selectionner une compétition"));
//		jButtons.add(new JButton("Retour"));
//		for(JButton button : jButtons) {
//			compet.add(button);
//			button.addActionListener(this);
//		}
//		return compet;
//	}
//	
//	private JPanel PersonneIhm() {
//		JPanel guy = new JPanel();
//		guy.setLayout(new FlowLayout());
//		ArrayList<JButton> jButtons = new ArrayList<>();
//		jButtons.add(new JButton("Créer un sportif"));
//		jButtons.add(new JButton("Afficher les sportifs"));
//		jButtons.add(new JButton("Selectionner un sportif"));
//		jButtons.add(new JButton("Retour"));
//		for(JButton button : jButtons) {
//			guy.add(button);
//			button.addActionListener(this);
//		}
//		return guy;
//	}
//	
//	private JPanel CreationPersonneIhm() {
//		JPanel createGuy = new JPanel();
//		lname.setPreferredSize( new Dimension( 200, 24 ) );
//		fname.setPreferredSize( new Dimension( 200, 24 ) );
//		mail.setPreferredSize( new Dimension( 200, 24 ) );
//		createGuy.setBorder(BorderFactory.createTitledBorder("Création du sportif"));
//		createGuy.add(new JLabel("Nom : "));
//		createGuy.add(lname);
//		createGuy.add(new JLabel("Prenom : "));
//		createGuy.add(fname);
//		createGuy.add(new JLabel("Mail : "));
//		createGuy.add(mail);
//		ArrayList<JButton> jButtons = new ArrayList<>();
//		jButtons.add(new JButton("Retour"));
//		for(JButton button : jButtons) {
//			createGuy.add(button);
//			button.addActionListener(this);
//		}
//		return createGuy;
//	}
//	
//	private KeyListener getKeyListener() {
//		return new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				afficheCarre();
//			}
//		};
//	}
//	
//	private void afficheCarre()
//	{
//		try
//		{
//			int k = Integer.parseInt(lname.getText());
//			k *= k;
//			result.setText("" + k);
//		}
//		catch (NumberFormatException e)
//		{
//			result.setText("");
//		}
//	}
	
	public static void main(String[] args)
	{
		new Interface();
	}

}
