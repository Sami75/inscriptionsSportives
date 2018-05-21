package ihm;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.mail.SendFailedException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import back.Contact;
import back.Passerelle;

import inscriptions.Inscriptions;
import inscriptions.Personne;

public class SportifIhm extends JPanel implements ActionListener {

	JLabel nameLabel = new JLabel("Nom : ");
	JLabel fnameLabel = new JLabel("Prenom : ");
	JLabel mailLabel = new JLabel("Mail : ");
	JLabel destinataire = new JLabel("A : ");
	JLabel object = new JLabel("Objet : ");
	JLabel message = new JLabel("Message : ");
	JButton contactGuy = new JButton("Contacter");
	JTextField nameField = new JTextField();
	JTextField fnameField = new JTextField();
	JTextField mailField = new JTextField();
	JTextField objectField = new JTextField();
	JTextPane messageField = new JTextPane();
	DefaultListModel listMails = new DefaultListModel();
	JComboBox list = new JComboBox();
	JList fullList = new JList();
	Inscriptions inscriptions;
	Personne personne;
	JFrame frame;
	
	public SportifIhm(JFrame frame, Inscriptions inscriptions) {
		this.frame = frame;
		this.inscriptions = inscriptions;
		initSportifIhm();
	}
	
	public void initSportifIhm() {
		JButton createGuy = new JButton("Créer un sportif");
		JButton listGuy = new JButton("Lister les sportifs");
		JButton selectGuy = new JButton("Selectionner un sportif");
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
		buttons.add(createGuy, gbc);
		buttons.add(listGuy, gbc);
		buttons.add(selectGuy, gbc);
		buttons.add(contactGuy, gbc);
		buttons.add(retour, gbc);

		gbc.weighty = 1;
		add(buttons, gbc);
		createGuy.addActionListener(this);
		listGuy.addActionListener(this);
		selectGuy.addActionListener(this);
		contactGuy.addActionListener(this);
		retour.addActionListener(this);
	}
	
	public JPanel createGuyIhm() {
		 JButton createdGuy = new JButton("Créer");
		JButton retour = new JButton("Retour");
		JPanel createGuy = new JPanel();
		nameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		fnameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		mailLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		fnameField.setPreferredSize( new Dimension( 200, 24 ) );
		mailField.setPreferredSize( new Dimension( 200, 24 ) );
		
		JPanel buttons = new JPanel();
		
		buttons.setPreferredSize( new Dimension( 350, 160 ) );
		
		buttons.setBorder(BorderFactory.createTitledBorder("Création du sportif"));
		buttons.add(nameLabel);
		buttons.add(nameField);
		buttons.add(fnameLabel);
		buttons.add(fnameField);
		buttons.add(mailLabel);
		buttons.add(mailField);

		buttons.add(retour);
		buttons.add(createdGuy);
		
		createGuy.add(buttons);

		createdGuy.addActionListener(this);
		retour.addActionListener(this);
		
		return createGuy;

	}
	
	public JPanel listGuysIhm() {
		JButton retour = new JButton("Retour");
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
		
		retour.addActionListener(this);

		return listGuys;
	}
	
	public JPanel selectGuyIhm() {
		JButton selectedGuy = new JButton("Valider");
		JButton retour = new JButton("Retour");
		JPanel selectGuy = new JPanel();
		ArrayList<Personne> guys = new ArrayList<Personne>();
		guys = (ArrayList) Passerelle.getData("Personne");

        for(Personne p : guys) {
			list.addItem(p);
		}
        selectGuy.add(new JLabel("Selectionner un sportif : "));
		selectGuy.add(list);
		selectGuy.add(retour);
		selectGuy.add(selectedGuy);
		
		selectedGuy.addActionListener(this);
		retour.addActionListener(this);
		return selectGuy;
	}
	
	public JPanel selectedGuyIhm(Personne personne) {
		JButton deleteGuy = new JButton("Supprimer");
		JButton editGuy = new JButton("Editer le sportif");
		JButton retour = new JButton("Retour");
		JPanel selectedGuy = new JPanel(new GridBagLayout());
		this.personne = personne;
		selectedGuy.setBorder(new EmptyBorder(10, 10, 10, 10));
		selectedGuy.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		selectedGuy.add(new JLabel("<html><h1><strong><i>Inscription Sportive</i></strong></h1><hr></html>"), gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(3,3,3,3);
		
		selectedGuy.add(editGuy, gbc);
		selectedGuy.add(deleteGuy, gbc);
		selectedGuy.add(retour, gbc);

		gbc.weighty = 1;
		add(selectedGuy, gbc);
		editGuy.addActionListener(this);
		deleteGuy.addActionListener(this);
		retour.addActionListener(this);
		
		return selectedGuy;
	}
	
	public JPanel editGuy(Personne personne) {
		JButton editedGuy = new JButton("Editer");
		JButton retour = new JButton("Retour");
		JPanel editGuy = new JPanel();
		this.personne = personne;
		
		nameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		fnameLabel.setPreferredSize( new Dimension( 70, 24 ) );
		mailLabel.setPreferredSize( new Dimension( 70, 24 ) );
		nameField.setPreferredSize( new Dimension( 200, 24 ) );
		fnameField.setPreferredSize( new Dimension( 200, 24 ) );
		mailField.setPreferredSize( new Dimension( 200, 24 ) );
		
		JPanel buttons = new JPanel();
		
		buttons.setPreferredSize( new Dimension( 350, 160 ) );
		
		nameField.setText(personne.getNom());
		fnameField.setText(personne.getPrenom());
		mailField.setText(personne.getMail());
		
		buttons.setBorder(BorderFactory.createTitledBorder("Edition du sportif"));
		buttons.add(nameLabel);
		buttons.add(nameField);
		buttons.add(fnameLabel);
		buttons.add(fnameField);
		buttons.add(mailLabel);
		buttons.add(mailField);

		buttons.add(retour);
		buttons.add(editedGuy);
		editGuy.add(buttons);

		editedGuy.addActionListener(this);
		retour.addActionListener(this);
		
		return editGuy;

	}
	
	public JPanel contactGuyIhm() {
		JButton sendMail = new JButton("Envoyer");
		JButton retour = new JButton("Retour");
		JPanel contact = new JPanel();
		JPanel button = new JPanel();
		JPanel border = new JPanel();
		
		border.setBorder(BorderFactory.createTitledBorder("Contact"));
		ArrayList<Personne> guys = new ArrayList<Personne>();
		guys = (ArrayList) Passerelle.getData("Personne");
		listMails.clear();
        for(Personne p : guys) {
			listMails.addElement(p.getMail());
		}
		fullList = new JList(listMails);
		fullList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		fullList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		fullList.setVisibleRowCount(-1);
		
		JScrollPane jsp = new JScrollPane(messageField);
		JScrollPane listScroller = new JScrollPane(fullList);
		destinataire.setPreferredSize(new Dimension(70, 24));
		listScroller.setPreferredSize(new Dimension(700, 80));
		object.setPreferredSize(new Dimension(70, 24));
		objectField.setPreferredSize(new Dimension(700, 24));
		message.setPreferredSize(new Dimension(70, 24));
		messageField.setPreferredSize(new Dimension(700, 300));
		
		messageField.setCaretPosition(0);
		border.setPreferredSize( new Dimension( 800, 500 ) );
		
		border.add(destinataire);
		border.add(listScroller);
		border.add(object);
		border.add(objectField);
		border.add(message);
		border.add(jsp);
		border.add(retour);
		border.add(sendMail);
		
		contact.add(border);
		
		retour.addActionListener(this);
		sendMail.addActionListener(this);
		
		return contact;
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
					
				case "Créer":
					System.out.println(((JButton) e.getSource()).getText());
					inscriptions.createPersonne(nameField.getText(), fnameField.getText(), mailField.getText());
					frame.getContentPane().removeAll();
					JOptionPane.showMessageDialog(frame,
						    "Le sportif : " + nameField.getText() + " " + fnameField.getText() + " a bien été créé ! Son mail est : " + mailField.getText());
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
					
				case "Valider":
					System.out.println(((JButton) e.getSource()).getText() + " Sportif : " + list.getSelectedItem());
					frame.getContentPane().removeAll();
					if(list.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(frame,
							    "Veuillez séléctionner un sportif, pour accéder à cette page !",
							    "Attention !",
							    JOptionPane.WARNING_MESSAGE);
						frame.setContentPane(new SportifIhm(frame, inscriptions));
					}
					else {
						frame.setContentPane(selectedGuyIhm((Personne) list.getSelectedItem()));
					}
					frame.invalidate();
					frame.validate();
					break;
					
				case "Editer le sportif":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(editGuy((Personne) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Supprimer":
					System.out.println(((JButton) e.getSource()).getText());
					int s = JOptionPane.showConfirmDialog(
						    frame,
						    "Etes-vous sûr de vouloir supprimer : " + personne.getNom() + " " + personne.getPrenom() + " ?",
						    "Confirmation",
						    JOptionPane.YES_NO_OPTION);
					if(s == 0) {
						personne.delete();
						JOptionPane.showMessageDialog(frame,
							    "Le sportif a bien été supprimé !");
						frame.getContentPane().removeAll();
						frame.setContentPane(new SportifIhm(frame, inscriptions));
						frame.invalidate();
						frame.validate();
						
					}
					break;
					
				case "Editer":
					System.out.println(((JButton) e.getSource()).getText());
					personne.setNom(nameField.getText());
					personne.setPrenom(fnameField.getText()); 
					personne.setMail(mailField.getText());
					JOptionPane.showMessageDialog(frame,
						    "Le sportif : " + nameField.getText() + " " + fnameField.getText() + " a bien été édité ! Son mail est : " + mailField.getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(selectedGuyIhm((Personne) list.getSelectedItem()));
					frame.invalidate();
					frame.validate();
					break;
					
				case "Contacter":
					System.out.println(((JButton) e.getSource()).getText());
					frame.getContentPane().removeAll();
					frame.setContentPane(contactGuyIhm());
					frame.invalidate();
					frame.validate();
					break;
					
				case "Envoyer":
					System.out.println(((JButton) e.getSource()).getText());
					Object[] mails =  fullList.getSelectedValuesList().toArray();
					if(mails.length == 0) {
						JOptionPane.showMessageDialog(frame,
							    "Veuillez séléctionner un sportif, avant de tenter d'envoyer un mail ! Si vous souhaitez envoyer un mail groupé, faite ctrl + clic gauche sur chaque mail !",
							    "Attention !",
							    JOptionPane.ERROR_MESSAGE);
						frame.getContentPane().removeAll();
						frame.setContentPane(contactGuyIhm());
						frame.invalidate();
						frame.validate();
					}
					else {
						for(int i = 0, n = mails.length; i < n; i++) {
							String mail = (String) mails[i];
							Contact.sendMail(mail, objectField.getText(), messageField.getText(), frame);
						}
					
						frame.getContentPane().removeAll();
						frame.setContentPane(new SportifIhm(frame, inscriptions));
						frame.invalidate();
						frame.validate();
					}
					break;				
		}
	}
}