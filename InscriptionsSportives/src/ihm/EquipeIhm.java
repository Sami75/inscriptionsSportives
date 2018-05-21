package ihm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import back.Passerelle;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;
import inscriptions.Equipe;

public class EquipeIhm extends JPanel implements ActionListener {


	private JLabel nameLabel= new JLabel("Nom : ");
	private JTextField nameField = new JTextField();
	private JFrame frame;
	private Inscriptions inscriptions;
	private Equipe equipe;
	JComboBox list = new JComboBox();
	JComboBox listGuys = new JComboBox();
	JComboBox listMember = new JComboBox();

	public EquipeIhm(JFrame frame, Inscriptions inscriptions) {
		this.frame = frame;
		this.inscriptions = inscriptions;
		initEquipeIhm();
	}

	public void initEquipeIhm() {
		JButton createTeam = new JButton("Créer une équipe");
		JButton listTeam = new JButton("Lister les équipes");
		JButton selectTeam = new JButton("Selectionner une équipe");
		JButton retour = new JButton("Retour");
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(3,3,3,3);
			
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
		JButton retour = new JButton("Retour");
		JButton createdTeam = new JButton("Créer");
		JPanel createTeam = new JPanel();
		nameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		
		JPanel buttons = new JPanel();
		
		buttons.setPreferredSize( new Dimension( 350, 100 ) );
		
		buttons.setBorder(BorderFactory.createTitledBorder("Création du sportif"));
		buttons.add(nameLabel);
		buttons.add(nameField);

		buttons.add(retour);
		buttons.add(createdTeam);
		
		createTeam.add(buttons);

		createdTeam.addActionListener(this);
		retour.addActionListener(this);
		
		return createTeam;

	}
	
	public JPanel listTeamsIhm() {
		JButton retour = new JButton("Retour");
		JPanel listTeams = new JPanel();
		DefaultListModel list = new DefaultListModel();
		JList fullList = new JList(list);
		ArrayList<Equipe> teams = new ArrayList<Equipe>();
		teams = (ArrayList) Passerelle.getData("Equipe");
		for(Equipe e : teams) {
			list.addElement(e);
		}
		listTeams.setLayout(new FlowLayout());
		listTeams.add(new JLabel("Liste des équipes : "));
		listTeams.add(fullList);
		listTeams.add(retour);
		
		retour.addActionListener(this);

		return listTeams;
	}
	
	public JPanel selectTeamIhm() {
		JButton selectedTeam = new JButton("Valider");
		JButton retour = new JButton("Retour");
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
		retour.addActionListener(this);
		return selectTeam;
	}
	
	public JPanel selectedTeamIhm(Equipe equipe) {
		JButton editTeam = new JButton("Editer l'équipe");
		JButton deleteTeam = new JButton("Supprimer");
		JButton listMemberTeam = new JButton("Lister les membres de l'équipe");
		JButton addGuyTeam = new JButton("Ajouter un sportif à l'équipe");
		JButton selectedGuy = new JButton("Supprimer un sportif de l'équipe");
		JButton retour = new JButton("Retour");
		this.equipe = equipe;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		JPanel selectedTeam = new JPanel(new GridBagLayout());
		selectedTeam.setBorder(new EmptyBorder(10, 10, 10, 10));
		selectedTeam.setLayout(new GridBagLayout());
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(3,3,3,3);
		
		selectedTeam.add(editTeam, gbc);
		selectedTeam.add(deleteTeam, gbc);
		selectedTeam.add(selectedGuy, gbc);
		selectedTeam.add(listMemberTeam, gbc);
		selectedTeam.add(addGuyTeam, gbc);
		selectedTeam.add(retour, gbc);

		gbc.weighty = 1;
		add(selectedTeam, gbc);
		editTeam.addActionListener(this);
		deleteTeam.addActionListener(this);
		selectedGuy.addActionListener(this);
		retour.addActionListener(this);
		listMemberTeam.addActionListener(this);
		addGuyTeam.addActionListener(this);
		retour.addActionListener(this);
		
		return selectedTeam;
	}
	
	public JPanel editTeam(Equipe equipe) {
		JButton editedTeam = new JButton("Editer");
		JButton retour = new JButton("Retour");
		JPanel editTeam = new JPanel();
		this.equipe = equipe;
		
		nameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		nameField.setText(equipe.getNom());
		
		JPanel buttons = new JPanel();
		
		buttons.setPreferredSize( new Dimension( 350, 160 ) );

		buttons.setBorder(BorderFactory.createTitledBorder("Edition du sportif"));
		buttons.add(nameLabel);
		buttons.add(nameField);

		buttons.add(retour);
		buttons.add(editedTeam);
		
		editTeam.add(buttons);

		editedTeam.addActionListener(this);
		retour.addActionListener(this);
		
		return editTeam;
	}
	
	public JPanel deleteGuyTeam(Equipe equipe) {
		JButton deleteGuy = new JButton("Supprimer le sportif");
		JButton retour = new JButton("Retour");
		JPanel selectGuy = new JPanel();
		ArrayList<Personne> team = new ArrayList<Personne>();
		this.equipe = equipe;

        for(Personne p : equipe.getMembres()) {
			listMember.addItem(p);
		}
        
        selectGuy.add(new JLabel("Selectionner un sportif : "));
        selectGuy.add(listMember);
        selectGuy.add(retour);
        selectGuy.add(deleteGuy);
        
        deleteGuy.addActionListener(this);
        retour.addActionListener(this);
		
		return selectGuy;
	}
	
	public JPanel addGuyTeam(Equipe equipe) {
		JButton added = new JButton("Ajouter");
		JButton retour = new JButton("Retour");
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
        retour.addActionListener(this);
		
		return addGuyTeam;
	}
	
	public JPanel listMemberTeam(Equipe equipe) {
		JButton retour = new JButton("Retour");
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
		
		retour.addActionListener(this);
		
		return listMemberTeam;
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
					JOptionPane.showMessageDialog(frame,
						    "L'équipe : " + nameField.getText() + " a bien été créé !");
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
					if(list.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(frame,
							    "Veuillez séléctionner une équipe, pour accéder à cette page !",
							    "Attention !",
							    JOptionPane.WARNING_MESSAGE);
						frame.setContentPane(new EquipeIhm(frame, inscriptions));
					}
					else {
						frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
					}
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
					int s = JOptionPane.showConfirmDialog(
						    frame,
						    "Etes-vous sûr de vouloir supprimer : " + equipe.getNom() + " ?",
						    "Confirmation",
						    JOptionPane.YES_NO_OPTION);
					if(s == 0) {
						equipe.delete();
						JOptionPane.showMessageDialog(frame,
							    "L'équipe a bien été supprimée !");
						frame.getContentPane().removeAll();
						frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
						frame.invalidate();
						frame.validate();
					}
					break;
					
				case "Editer":
					System.out.println(((JButton) e.getSource()).getText());
					equipe.setNom(nameField.getText());			
					JOptionPane.showMessageDialog(frame,
						    "L'équipe : " + nameField.getText() + " a bien été édité ! ");
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
					
				case "Supprimer un sportif de l'équipe":
					System.out.println(((JButton) e.getSource()).getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(deleteGuyTeam((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Supprimer le sportif":
					System.out.println(((JButton) e.getSource()).getText());
					if(listMember.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(frame,
							    "Veuillez séléctionner une équipe, pour accéder à cette page !",
							    "Attention !",
							    JOptionPane.WARNING_MESSAGE);
						frame.setContentPane(new EquipeIhm(frame, inscriptions));
					}
					else {
						int t = JOptionPane.showConfirmDialog(frame,
							    "Etes-vous sûr de vouloir supprimer le sportif de l'équipe ?",
							    "Confirmation",
							    JOptionPane.YES_NO_OPTION);
						if(t == 0) {
							equipe.remove((Personne) listMember.getSelectedItem());
							JOptionPane.showMessageDialog(frame,
								    "Le sportif a bien été supprimer de l'équipe !");
						frame.getContentPane().removeAll();
						frame.setContentPane(deleteGuyTeam((Equipe) list.getSelectedItem()));
						frame.invalidate();
						frame.validate();
						break;
						}
					}
						   
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedTeamIhm((Equipe) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
			}
	}
}
