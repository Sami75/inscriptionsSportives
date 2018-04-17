package ihm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SportifIhm extends JPanel implements ActionListener {

	private JButton createGuy = new JButton("Créer un sportif");
	private JButton listGuy = new JButton("Lister les sportifs");
	private JButton selectGuy = new JButton("Selectionner un sportif");
	private JButton retour = new JButton("Retour");
	private JLabel nameLabel = new JLabel("Nom : ");
	private JLabel fnameLabel = new JLabel("Prenom : ");
	private JLabel mailLabel = new JLabel("Mail : ");
	JTextField nameField = new JTextField();
	JTextField fnameField = new JTextField();
	JTextField mailField = new JTextField();
	
	
	private JFrame frame;
	
	public SportifIhm(JFrame frame) {
		this.frame = frame;
		initSportifIhm();
	}
	
	public void initSportifIhm() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.add(createGuy, gbc);
		buttons.add(listGuy, gbc);
		buttons.add(selectGuy, gbc);
		buttons.add(retour, gbc);

		gbc.weighty = 1;
		add(buttons, gbc);
		createGuy.addActionListener(this);
		listGuy.addActionListener(this);
		selectGuy.addActionListener(this);
		retour.addActionListener(this);
	}
	
	public JPanel createGuyIhm() {
		JPanel createGuy = new JPanel();
		nameLabel.setPreferredSize( new Dimension( 200, 24 ) );
		fnameLabel.setPreferredSize( new Dimension( 200, 24 ) );
		mailLabel.setPreferredSize( new Dimension( 200, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		fnameField.setPreferredSize( new Dimension( 200, 24 ) );
		mailField.setPreferredSize( new Dimension( 200, 24 ) );
		
		createGuy.setBorder(BorderFactory.createTitledBorder("Création du sportif"));
		createGuy.add(nameLabel);
		createGuy.add(nameField);
		createGuy.add(fnameLabel);
		createGuy.add(fnameField);
		createGuy.add(mailLabel);
		createGuy.add(mailField);

		createGuy.add(retour);
		
		return createGuy;

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
					
				case "Créer un sportif":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(createGuyIhm());
					frame.invalidate();
					frame.validate();
					
			}
	}

}
