package ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import back.Passerelle;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class CompetitionIhm extends JPanel implements ActionListener {

	JLabel nameLabel = new JLabel("Nom : ");
	JLabel typeLabel = new JLabel("Type : ");
	JLabel dateLabel = new JLabel("Date de clôture : ");
	JTextField nameField = new JTextField();
	JComboBox list = new JComboBox();
	JComboBox listTeams = new JComboBox();
	JComboBox listGuys = new JComboBox();
	JComboBox listCandidat = new JComboBox();
	String[] type = { "en équipe", "individuel"};
	JComboBox listType = new JComboBox(type);
	JFrame frame;
	JDatePickerImpl datePicker;
	boolean enEquipe;
	Inscriptions inscriptions;
	Competition competition;
			
	public CompetitionIhm(JFrame frame, Inscriptions inscriptions) {
		this.frame = frame;
		this.inscriptions = inscriptions;
		initCompetitionIhm();
	}
	
	public void initCompetitionIhm() {
		JButton createCompetition = new JButton("Créer une compétition");
		JButton listCompetition = new JButton("Lister les compétitions");
		JButton selectCompetition = new JButton("Selectionner une compétition");
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
		buttons.add(createCompetition, gbc);
		buttons.add(listCompetition, gbc);
		buttons.add(selectCompetition, gbc);
		buttons.add(retour, gbc);

		gbc.weighty = 1;
		add(buttons, gbc);
		createCompetition.addActionListener(this);
		listCompetition.addActionListener(this);
		selectCompetition.addActionListener(this);
		retour.addActionListener(this);
	}

	public JPanel createCompetitionIhm() {
		JButton createdCompetition = new JButton("Créer");
		JButton retour = new JButton("Retour");
		JPanel createCompetition = new JPanel();
		
		nameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		dateLabel.setPreferredSize( new Dimension( 70, 24 ) );
		typeLabel.setPreferredSize( new Dimension( 70, 24 ) );
		listType.setPreferredSize( new Dimension( 200, 24 ) );
		((JLabel)listType.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setPreferredSize( new Dimension( 350, 160 ) );
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Aujourd'hui");
		p.put("text.month", "Mois");
		p.put("text.year", "Année");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		buttons.setBorder(BorderFactory.createTitledBorder("Création de la compétition"));
		buttons.add(nameLabel);
		buttons.add(nameField);
		buttons.add(dateLabel);
		buttons.add(datePicker);
		buttons.add(typeLabel);
		buttons.add(listType);
		buttons.add(retour);
		buttons.add(createdCompetition);
		this.datePicker = datePicker;
		
		createCompetition.add(buttons);
		retour.addActionListener(this);
		createdCompetition.addActionListener(this);
		
		return createCompetition;

	}
	
	public JPanel listCompetitionsIhm() {
		JButton retour = new JButton("Retour");
		JPanel listCompetitions = new JPanel();
		DefaultListModel list = new DefaultListModel();
		JList fullList = new JList(list);
		ArrayList<Competition> competitions = new ArrayList<Competition>();
		competitions = (ArrayList) Passerelle.getData("Competition");
		for(Competition c : competitions) {
			list.addElement(c);
		}
		listCompetitions.setLayout(new FlowLayout());
		listCompetitions.add(new JLabel("Liste des compétitions : "));
		listCompetitions.add(fullList);
		listCompetitions.add(retour);
		retour.addActionListener(this);
		return listCompetitions;
	}
	
	public JPanel selectCompetitionIhm() {
		JButton selectedCompetition = new JButton("Valider");
		JButton retour = new JButton("Retour");
		JPanel selectCompetition = new JPanel();
		ArrayList<Competition> competitions = new ArrayList<Competition>();
		competitions = (ArrayList) Passerelle.getData("Competition");

        for(Competition c : competitions) {
			list.addItem(c);
		}
        selectCompetition.add(new JLabel("Selectionner un sportif : "));
		selectCompetition.add(list);
		selectCompetition.add(retour);
		selectCompetition.add(selectedCompetition);
		
		selectedCompetition.addActionListener(this);
		retour.addActionListener(this);
		return selectCompetition;
	}
	
	public JPanel selectedCompetitionIhm(Competition competition) {
		JButton editCompetition = new JButton("Editer la compétition");
		JButton detailsCompet = new JButton("Informations détaillées sur la compétition");
		JButton addCandidatCompetition = new JButton("Ajouter des candidats à la compétition");
		JButton deleteCompetition = new JButton("Supprimer");
		JButton selectCandidatCompetition = new JButton("Désincrire un candidat de la compétition");
		JButton retour = new JButton("Retour");
		JPanel selectedCompetition = new JPanel(new GridBagLayout());
		this.competition = competition;
		selectedCompetition.setBorder(new EmptyBorder(10, 10, 10, 10));
		selectedCompetition.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		selectedCompetition.add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(3,3,3,3);
		
		selectedCompetition.add(editCompetition, gbc);
		selectedCompetition.add(deleteCompetition, gbc);
		selectedCompetition.add(selectCandidatCompetition, gbc);
		selectedCompetition.add(detailsCompet, gbc);
		selectedCompetition.add(addCandidatCompetition, gbc);
		selectedCompetition.add(retour, gbc);

		gbc.weighty = 1;
		add(selectedCompetition, gbc);
		editCompetition.addActionListener(this);
		deleteCompetition.addActionListener(this);
		retour.addActionListener(this);
		detailsCompet.addActionListener(this);
		selectCandidatCompetition.addActionListener(this);
		addCandidatCompetition.addActionListener(this);
		retour.addActionListener(this);
		
		return selectedCompetition;
	}
	
	public JPanel editCompetition(Competition competition) {
		JButton retour = new JButton("Retour");
		JButton editedCompetition = new JButton("Editer");
		JPanel editCompetition = new JPanel();
		this.competition = competition;
		
		nameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		dateLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setText(competition.getNom());

		JPanel buttons = new JPanel();
		buttons.setPreferredSize( new Dimension( 350, 160 ) );
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Aujourd'hui");
		p.put("text.month", "Mois");
		p.put("text.year", "Année");
		model.setValue(competition.getDateCloture());
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		buttons.setBorder(BorderFactory.createTitledBorder("Edition de la compétition : " + competition.getNom()));
		buttons.add(nameLabel);
		buttons.add(nameField);
		buttons.add(dateLabel);
		buttons.add(datePicker);
		buttons.add(retour);
		buttons.add(editedCompetition);
		this.datePicker = datePicker;

		editCompetition.add(buttons);
		retour.addActionListener(this);
		editedCompetition.addActionListener(this);
		
		return editCompetition;
	}
	
	public JPanel detailsCompetIhm(Competition competition) {
		JButton retour = new JButton("Retour");
		JPanel detailsCompet = new JPanel();
		this.competition = competition;
		
		DefaultListModel list = new DefaultListModel();
		JList fullList = new JList(list);
		list.addElement("Nom : " + competition.getNom());
		list.addElement("Date de clôture : " + competition.getDateCloture());
		if(competition.estEnEquipe()) 
			list.addElement("Type : en équipe");
		else
			list.addElement("Type : individuel");
		list.addElement("Candidats inscrits : " + competition.getCandidats());
		
		detailsCompet.setLayout(new FlowLayout());
		detailsCompet.add(new JLabel("Information : "));
		detailsCompet.add(fullList);
		detailsCompet.add(retour);
		retour.addActionListener(this);
		return detailsCompet;
	}
	
	public JPanel addCandidatCompetition(Competition competition) {
		JButton retour = new JButton("Retour");
		JButton added = new JButton("Ajouter");
		JPanel addCompetition = new JPanel();
		this.competition = competition;
		listTeams.removeAllItems();
		listGuys.removeAllItems();
		if(competition.estEnEquipe()) {
			ArrayList<Equipe> teams = new ArrayList<Equipe>();
			teams = (ArrayList) Passerelle.getData("Equipe");
			for(Equipe e : teams) {
				listTeams.addItem(e);					
			}
		}
		else {
			ArrayList<Personne> guys = new ArrayList<Personne>();
			guys = (ArrayList) Passerelle.getData("Personne");
			for(Personne p : guys) {
				listGuys.addItem(p);
			}
		}

        
        addCompetition.add(new JLabel("Selectionner un candidat : "));
        if(competition.estEnEquipe()) 
        	addCompetition.add(listTeams);
        else
        	addCompetition.add(listGuys);
        addCompetition.add(retour);
        addCompetition.add(added);
		
        added.addActionListener(this);
        retour.addActionListener(this);
		return addCompetition;
	}
	
	public JPanel deleteCandidatCompetition(Competition competition) {
		JButton deleteCandidatCompetition = new JButton("Désincrire le candidat");
		JButton retour = new JButton("Retour");
		JPanel selectCandidat = new JPanel();
		this.competition = competition;

        selectCandidat.add(new JLabel("Selectionner un candidat : "));
        
        for(Candidat c : competition.getCandidats()) 
        	listCandidat.addItem(c);
        	
        selectCandidat.add(listTeams);
        selectCandidat.add(retour);
        selectCandidat.add(deleteCandidatCompetition);
		
		deleteCandidatCompetition.addActionListener(this);
		retour.addActionListener(this);
		return selectCandidat;
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

				case "Créer une compétition":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(createCompetitionIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Créer":
					enEquipe = listType.getSelectedItem().toString().equals("en équipe");
					System.out.println(((JButton) e.getSource()).getText());
					System.out.println(enEquipe + " " + listType.getSelectedItem());
					inscriptions.createCompetition(nameField.getText(), (Date) datePicker.getModel().getValue(), enEquipe);
					frame.getContentPane().removeAll();
					frame.setContentPane(new CompetitionIhm(frame, inscriptions));
					frame.invalidate();
					frame.validate();
					break;
				
				case "Lister les compétitions":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(listCompetitionsIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Selectionner une compétition":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(selectCompetitionIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Valider":
					System.out.println(((JButton) e.getSource()).getText() + " Sportif : " + list.getSelectedItem());
					frame.getContentPane().removeAll();
					if(list.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(frame,
							    "Veuillez séléctionner une compétition, pour accéder à cette page !",
							    "Attention !",
							    JOptionPane.WARNING_MESSAGE);
						frame.setContentPane(new CompetitionIhm(frame, inscriptions));
					}
					else {
						frame.setContentPane(selectedCompetitionIhm((Competition) list.getSelectedItem()));
					}
					frame.invalidate();
					frame.validate();
					break;
					
				case "Editer la compétition":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(editCompetition((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Supprimer":
					System.out.println(((JButton) e.getSource()).getText());
					int s = JOptionPane.showConfirmDialog(
						    frame,
						    "Etes-vous sûr de vouloir supprimer : " + competition.getNom() + " ?",
						    "Confirmation",
						    JOptionPane.YES_NO_OPTION);
					if(s == 0) {
						competition.delete();
						JOptionPane.showMessageDialog(frame,
							    "La compétition a bien été supprimée !");
						frame.getContentPane().removeAll();
						frame.setContentPane(selectedCompetitionIhm((Competition) list.getSelectedItem()));
						frame.invalidate();
						frame.validate();
					}
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedCompetitionIhm((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Editer":
					System.out.println(((JButton) e.getSource()).getText());
					competition.setNom(nameField.getText());
					competition.setDateCloture((Date) datePicker.getModel().getValue());
					JOptionPane.showMessageDialog(frame,
						    "La compétition : " + competition.getNom() + " a bien été éditée ! ");
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedCompetitionIhm((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Informations détaillées sur la compétition":
					System.out.println(((JButton) e.getSource()).getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(detailsCompetIhm((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Ajouter des candidats à la compétition":
					System.out.println(((JButton) e.getSource()).getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(addCandidatCompetition((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Ajouter":
					System.out.println(((JButton) e.getSource()).getText());
					if(competition.estEnEquipe())
						competition.add(((Equipe) listTeams.getSelectedItem()));
					else
						competition.add(((Personne) listGuys.getSelectedItem()));
						
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedCompetitionIhm((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Désincrire un candidat de la compétition":
					System.out.println(((JButton) e.getSource()).getText());			
					frame.getContentPane().removeAll();
					frame.setContentPane(deleteCandidatCompetition((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Désincrire le candidat":
					System.out.println(((JButton) e.getSource()).getText());
					if(listCandidat.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(frame,
							    "Veuillez séléctionner un candidat !",
							    "Attention !",
							    JOptionPane.WARNING_MESSAGE);
						frame.setContentPane(new CompetitionIhm(frame, inscriptions));
					}
					else {
						int t = JOptionPane.showConfirmDialog(frame,
							    "Etes-vous sûr de vouloir désincrire le candidat de la compétition ?",
							    "Confirmation",
							    JOptionPane.YES_NO_OPTION);
						if(t == 0) {
							competition.remove((Candidat) listCandidat.getSelectedItem());
							JOptionPane.showMessageDialog(frame,
							    "Le candidat a bien été désinscrit de la compétition !");
							frame.getContentPane().removeAll();
							frame.setContentPane(deleteCandidatCompetition((Competition) list.getSelectedItem()));
							frame.invalidate();
							frame.validate();
							break;
							}
						}
					
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedCompetitionIhm((Competition) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
			}
	}

}