package ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import inscriptions.Inscriptions;

public class Accueil extends JPanel implements ActionListener{

	private JButton competition = new JButton("Compétition");
	private JButton equipe = new JButton("Equipe");
	private JButton sportif = new JButton("Sportif");
	private JButton quitter = new JButton("Quitter");
	private JFrame frame;	
	private Inscriptions inscriptions;
	
	public Accueil(JFrame frame, Inscriptions inscriptions) {
		this.frame = frame;
		this.inscriptions = inscriptions;
		initAccueil();
	}
	
	public void initAccueil() {
		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		buttons.add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		
		buttons.add(competition, gbc);
		buttons.add(equipe, gbc);
		buttons.add(sportif, gbc);
		buttons.add(quitter, gbc);

		
		gbc.weighty = 1;
		add(buttons, gbc);
		competition.addActionListener(this);
		equipe.addActionListener(this);
		sportif.addActionListener(this);
		quitter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		
		case "Quitter":
			Interface.close();
			break;
			
		case "Compétition":
			System.out.println(((JButton) e.getSource()).getText());
			frame.getContentPane().removeAll();
			frame.setContentPane(new CompetitionIhm(frame, inscriptions));
			frame.invalidate();
			frame.validate();
			break;
			
		case "Equipe":
			System.out.println(((JButton) e.getSource()).getText());
			frame.getContentPane().removeAll();
			frame.setContentPane(new EquipeIhm(frame, inscriptions));
			frame.invalidate();
			frame.validate();
			break;

		case "Sportif":
			System.out.println(((JButton) e.getSource()).getText());
			frame.getContentPane().removeAll();
			frame.setContentPane((new SportifIhm(frame, inscriptions)));
			frame.invalidate();
			frame.validate();
			break;
		}
	}
}
