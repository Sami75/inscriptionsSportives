package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CompetitionIhm extends JPanel implements ActionListener {

	private JButton createCompet = new JButton("Créer une compétition");
	private JButton listCompet = new JButton("Lister les compétitions");
	private JButton selectCompet = new JButton("Selectionner une compétition");
	private JButton retour = new JButton("Retour");
	private JFrame frame;
			
	public CompetitionIhm(JFrame frame) {
		this.frame = frame;
		initCompetitionIhm();
	}
	
	public void initCompetitionIhm() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.add(createCompet, gbc);
		buttons.add(listCompet, gbc);
		buttons.add(selectCompet, gbc);
		buttons.add(retour, gbc);

		gbc.weighty = 1;
		add(buttons, gbc);
		createCompet.addActionListener(this);
		listCompet.addActionListener(this);
		selectCompet.addActionListener(this);
		retour.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (((JButton) e.getSource()).getText()) {
				
				case "Retour":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(new Accueil(frame));
					frame.invalidate();
					frame.validate();
					break;
					
			}
	}

}
