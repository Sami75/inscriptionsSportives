package ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Interface extends JFrame implements ActionListener {

	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	public Interface() {
		
		frame.setTitle("Interface Inscription Sportive");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setContentPane(getMainPanel());
		frame.setVisible(true);
		frame.pack();
	}

	private JPanel getMainPanel() {
		// TODO Auto-generated method stub

		ArrayList<JButton> jButtons = new ArrayList<>();
		jButtons.add(new JButton("Menu Compétition"));
		jButtons.add(new JButton("Menu Equipe"));
		jButtons.add(new JButton("Menu Personne"));
		for(JButton button : jButtons) {
			panel.add(button);
			button.addActionListener(this);
		}
		return panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(((JButton) e.getSource()).getText());
		
		switch (((JButton) e.getSource()).getText()) {
		
			case "Menu Compétition":
				frame.getContentPane().removeAll();
				frame.setContentPane(CompetitionIhm());
				frame.invalidate();
				frame.validate();
				break;
				
			case "Retour":
				frame.getContentPane().removeAll();
				frame.setContentPane(getMainPanel());
				frame.invalidate();
				frame.validate();
				break;
				
			case "Menu Personne":
				frame.getContentPane().removeAll();
				frame.setContentPane(PersonneIhm());
				frame.invalidate();
				frame.validate();
				break;
				
			case "Créer un sportif":
				frame.getContentPane().removeAll();
				frame.setContentPane((CreationPersonneIhm()));
				frame.invalidate();
				frame.validate();
				break;
		}
	}
	
	private JPanel CompetitionIhm() {
		panel.setLayout(new FlowLayout());
		ArrayList<JButton> jButtons = new ArrayList<>();
		jButtons.add(new JButton("Créer une compétition"));
		jButtons.add(new JButton("Lister les compétitions"));
		jButtons.add(new JButton("Selectionner une compétition"));
		jButtons.add(new JButton("Retour"));
		for(JButton button : jButtons) {
			panel.add(button);
			button.addActionListener(this);
		}
		return panel;
	}
	
	private JPanel PersonneIhm() {
		panel.setLayout(new FlowLayout());
		ArrayList<JButton> jButtons = new ArrayList<>();
		jButtons.add(new JButton("Créer un sportif"));
		jButtons.add(new JButton("Afficher les sportifs"));
		jButtons.add(new JButton("Selectionner un sportif"));
		jButtons.add(new JButton("Retour"));
		for(JButton button : jButtons) {
			panel.add(button);
			button.addActionListener(this);
		}
		return panel;
	}
	
	private JPanel CreationPersonneIhm() {
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("Entrer le nom : "));
		ArrayList<JButton> jButtons = new ArrayList<>();
		jButtons.add(new JButton("Retour"));
		for(JButton button : jButtons) {
			panel.add(button);
			button.addActionListener(this);
		}
		return panel;
	}
	
	public static void main(String[] args)
	{
		new Interface();
	}

}
