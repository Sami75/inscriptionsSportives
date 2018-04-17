package ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EquipeIhm extends JPanel implements ActionListener {

	private JButton createTeam = new JButton("Créer une équipe");
	private JButton listTeam = new JButton("Lister les équipes");
	private JButton selectTeam = new JButton("Selectionner une équipe");
	private JButton retour = new JButton("Retour");
	private JFrame frame;

	public EquipeIhm(JFrame frame) {
		this.frame = frame;
		initEquipeIhm();
	}

	public void initEquipeIhm() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.add(createTeam, gbc);
		buttons.add(listTeam, gbc);
		buttons.add(selectTeam, gbc);
		buttons.add(retour, gbc);

		gbc.weighty = 1;
		add(buttons, gbc);
		createTeam.addActionListener(this);
		listTeam.addActionListener(this);
		selectTeam.addActionListener(this);
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
