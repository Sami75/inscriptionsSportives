package ihm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import back.Passerelle;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;
import inscriptions.Equipe;

public class EquipeIhm extends JPanel implements ActionListener {

	private JButton createTeam = new JButton("Créer une équipe");
	private JButton listTeam = new JButton("Lister les équipes");
	private JButton selectTeam = new JButton("Selectionner une équipe");
	private JButton retour = new JButton("Retour");
	private JButton selectedTeam = new JButton("Valider");
	private JButton createdTeam = new JButton("Créer");
	private JButton editTeam = new JButton("Editer l'équipe");
	private JButton deleteTeam = new JButton("Supprimer");
	private JButton listMemberTeam = new JButton("Lister les membres de l'équipe");
	private JButton editedTeam = new JButton("Editer");
	private JButton addGuyTeam = new JButton("Ajouter un sportif à l'équipe");
	private JButton added = new JButton("Ajouter");
	private JLabel nameLabel= new JLabel("Nom : ");
	private JTextField nameField = new JTextField();
	private JFrame frame;
	private Inscriptions inscriptions;
	private Equipe equipe;
	JComboBox list = new JComboBox();
	JComboBox listGuys = new JComboBox();

	public EquipeIhm(JFrame frame, Inscriptions inscriptions) {
		this.frame = frame;
		this.inscriptions = inscriptions;
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
	
	public JPanel createTeamIhm() {
		JPanel createTeam = new JPanel();
		nameLabel.setPreferredSize( new Dimension( 400, 24 ) );
		nameField.setPreferredSize( new Dimension( 400, 24 ) );
		
		
		createTeam.setBorder(BorderFactory.createTitledBorder("Création du sportif"));
		createTeam.add(nameLabel);
		createTeam.add(nameField);

		createTeam.add(retour);
		createTeam.add(createdTeam);

		createdTeam.addActionListener(this);
		
		return createTeam;

	}
	
	public JPanel listTeamsIhm() {
		JPanel listTeams = new JPanel();
		DefaultListModel list = new DefaultListModel();
		JList fullList = new JList(list);
		ArrayList<Equipe> teams = new ArrayList<Equipe>();
		teams = (ArrayList) Passerelle.getData("Equipe");
		for(Equipe e : teams) {
			list.addElement(e);
		}
		listTeams.setLayout(new FlowLayout());
		listTeams.add(new JLabel("Liste des sportifs : "));
		listTeams.add(fullList);
		listTeams.add(retour);

		return listTeams;
	}
	
	public JPanel selectTeamIhm() {
		JPanel selectTeam = new JPanel();
		ArrayList<Equipe> teams = new ArrayList<Equipe>();
		teams = (ArrayList) Passerelle.getData("Equipe");

        for(Equipe e : teams) {
			list.addItem(e);
		}
        selectTeam.add(new JLabel("Selectionner un sportif : "));
		selectTeam.add(list);
		selectTeam.add(retour);
		selectTeam.add(selectedTeam);
		
		selectedTeam.addActionListener(this);
		return selectTeam;
	}
	
	public JPanel selectedTeamIhm(Equipe equipe) {
		JPanel selectedTeam = new JPanel(new GridBagLayout());
		this.equipe = equipe;
		selectedTeam.setBorder(new EmptyBorder(10, 10, 10, 10));
		selectedTeam.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		selectedTeam.add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		selectedTeam.add(editTeam, gbc);
		selectedTeam.add(deleteTeam, gbc);
		selectedTeam.add(retour, gbc);
		selectedTeam.add(listMemberTeam, gbc);
		selectedTeam.add(addGuyTeam, gbc);

		gbc.weighty = 1;
		add(selectedTeam, gbc);
		editTeam.addActionListener(this);
		deleteTeam.addActionListener(this);
		retour.addActionListener(this);
		listMemberTeam.addActionListener(this);
		addGuyTeam.addActionListener(this);
		
		return selectedTeam;
	}
	
	public JPanel editTeam(Equipe equipe) {
		JPanel editTeam = new JPanel();
		this.equipe = equipe;
		
		nameLabel.setPreferredSize( new Dimension( 400, 24 ) );
		nameField.setPreferredSize( new Dimension( 400, 24 ) );
		nameField.setText(equipe.getNom());

		editTeam.setBorder(BorderFactory.createTitledBorder("Edition du sportif"));
		editTeam.add(nameLabel);
		editTeam.add(nameField);

		editTeam.add(retour);
		editTeam.add(editedTeam);

		editedTeam.addActionListener(this);
		
		return editTeam;
	}
	
	public JPanel listMemberTeam(Equipe equipe) {
		JPanel listMemberTeam = new JPanel();
		this.equipe = equipe;
		
		DefaultListModel list = new DefaultListModel();
		JList fullList = new JList(list);
		ArrayList<Equipe> teams = new ArrayList<Equipe>();
		teams = (ArrayList) Passerelle.getData("Equipe");
		for(Equipe e : teams) {
			list.addElement(e.getMembres());
		}
		listMemberTeam.setLayout(new FlowLayout());
		listMemberTeam.add(new JLabel("Liste des sportifs : "));
		listMemberTeam.add(fullList);
		listMemberTeam.add(retour);

		
		return listMemberTeam;
	}
	
	public JPanel addGuyTeam(Equipe equipe) {
		JPanel addGuyTeam = new JPanel();
		this.equipe = equipe;
		ArrayList<Personne> guys = new ArrayList<Personne>();
		guys = (ArrayList) Passerelle.getData("Personne");

        for(Personne p : guys) {
			listGuys.addItem(p);
		}
        addGuyTeam.add(new JLabel("Selectionner un sportif : "));
        addGuyTeam.add(listGuys);
        addGuyTeam.add(retour);
        addGuyTeam.add(added);
		
        added.addActionListener(this);
		
		return addGuyTeam;
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
					
				case "Créer une équipe":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(createTeamIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Créer":
					System.out.println(((JButton) e.getSource()).getText());
					inscriptions.createEquipe(nameField.getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(new EquipeIhm(frame, inscriptions));
					frame.invalidate();
					frame.validate();
					break;
				
				case "Lister les équipes":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(listTeamsIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Selectionner une équipe":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(selectTeamIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Valider":
					System.out.println(((JButton) e.getSource()).getText() + " Sportif : " + list.getSelectedItem());
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Editer l'équipe":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(editTeam((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Supprimer":
					System.out.println(((JButton) e.getSource()).getText());
					equipe.delete();
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Editer":
					System.out.println(((JButton) e.getSource()).getText());
					equipe.setNom(nameField.getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Lister les membres de l'équipe":
					System.out.println(((JButton) e.getSource()).getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(listMemberTeam((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Ajouter un sportif à l'équipe":
					System.out.println(((JButton) e.getSource()).getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(addGuyTeam((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Ajouter":
					System.out.println(((JButton) e.getSource()).getText());			
					equipe.add(((Personne) listGuys.getSelectedItem()));
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
			}
	}
}
