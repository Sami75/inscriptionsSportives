package ihm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;

import back.Passerelle;
import commandLineMenus.List;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class SportifIhm extends JPanel implements ActionListener {

	private JButton createGuy = new JButton("Créer un sportif");
	private JButton listGuy = new JButton("Lister les sportifs");
	private JButton selectGuy = new JButton("Selectionner un sportif");
	private JButton retour = new JButton("Retour");
	private JButton createdGuy = new JButton("Créer le sportif");
	private JLabel nameLabel = new JLabel("Nom : ");
	private JLabel fnameLabel = new JLabel("Prenom : ");
	private JLabel mailLabel = new JLabel("Mail : ");
	JTextField nameField = new JTextField();
	JTextField fnameField = new JTextField();
	JTextField mailField = new JTextField();
	private Inscriptions inscriptions;
	
	
	private JFrame frame;
	
	public SportifIhm(JFrame frame, Inscriptions inscriptions) {
		this.frame = frame;
		this.inscriptions = inscriptions;
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
		nameLabel.setPreferredSize( new Dimension( 400, 24 ) );
		fnameLabel.setPreferredSize( new Dimension( 400, 24 ) );
		mailLabel.setPreferredSize( new Dimension( 400, 24 ) );
		nameField.setPreferredSize( new Dimension( 400, 24 ) );
		fnameField.setPreferredSize( new Dimension( 400, 24 ) );
		mailField.setPreferredSize( new Dimension( 400, 24 ) );
		
		createGuy.setBorder(BorderFactory.createTitledBorder("Création du sportif"));
		createGuy.add(nameLabel);
		createGuy.add(nameField);
		createGuy.add(fnameLabel);
		createGuy.add(fnameField);
		createGuy.add(mailLabel);
		createGuy.add(mailField);

		createGuy.add(retour);
		createGuy.add(createdGuy);

		createdGuy.addActionListener(this);
		
		return createGuy;

	}
	
	public JPanel listGuysIhm() {
		JPanel listGuys = new JPanel();
		DefaultListModel list = new DefaultListModel();
		JList fullList = new JList(list);
		ArrayList<Personne> guys = new ArrayList<Personne>();
		guys = (ArrayList) Passerelle.getData("Personne");
		for(Personne p : guys) {
			list.addElement(p);
		}
		listGuys.setLayout(new FlowLayout());
		listGuys.add(new JLabel("Liste des sportifs : "));
		listGuys.add(fullList);
		listGuys.add(retour);

		return listGuys;
	}
	
	public JPanel selectGuyIhm() {
		JPanel selectGuy = new JPanel();
		ArrayList<Personne> guys = new ArrayList<Personne>();
		guys = (ArrayList) Passerelle.getData("Personne");
        JComboBox list = new JComboBox();

        for(Personne p : guys) {
			list.addItem(p.getNom());
		}
        selectGuy.add(new JLabel("Selectionner un sportif : "));
		selectGuy.add(list);
		selectGuy.add(retour);
		return selectGuy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
				
				case "Retour":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(new Accueil(frame, inscriptions));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Créer un sportif":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(createGuyIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Créer le sportif":
					System.out.println(((JButton) e.getSource()).getText());
					inscriptions.createPersonne(nameField.getText(), fnameField.getText(), mailField.getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(new SportifIhm(frame, inscriptions));
					frame.invalidate();
					frame.validate();
					break;
				
				case "Lister les sportifs":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(listGuysIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Selectionner un sportif":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(selectGuyIhm());
					frame.invalidate();
					frame.validate();
					break;
					
		}
	}

}
