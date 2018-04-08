package MenuInscriptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedSet;

import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

import back.Passerelle;

public class InscriptionsSportiveConsole {

	private Inscriptions inscriptions;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public InscriptionsSportiveConsole(Inscriptions inscriptions) {
		this.inscriptions = inscriptions;
	}
	
//	public Option autoSaveOption() {
//		return new Option("Sauvegarder", "x", autoSaveAction());
//	}
//	
//	private Action autoSaveAction() {
//		return new Action() {
//			public void optionSelected() {
//				try
//				{
//					inscriptions.sauvegarder();
//				} 
//				catch (IOException e)
//				{
//					System.out.println("Sauvegarde impossible." + e);
//				}
//			}
//		};
//	}
	
	public Menu MenuPrincipal() {
		
	Menu rootMenu = new Menu("Inscription Sportive");

	rootMenu.add(menuCompetition());
	rootMenu.add(menuEquipe());
	rootMenu.add(menuPersonne());
	rootMenu.addQuit("q");
	
	return rootMenu;
	
	}
	
	public void start() {
		
		MenuPrincipal().start();
	}
	
	private Menu menuCompetition() {
		Menu menuCompetition = new Menu("Menu Compétition", "c");
		
		menuCompetition.add(createCompetOption());
		menuCompetition.add(listCompetOption());
		menuCompetition.add(selectCompet());
//		menuCompetition.add(autoSaveOption());
		menuCompetition.addBack("b");
		return menuCompetition;
	}
	
	public Option createCompetOption() {
		return new Option("Créer une compétition", "c", createCompetAction());
	}
	
	private Action createCompetAction() {
		return new Action() {
			public void optionSelected() {
				
				String nomCompet = InOut.getString("Entrer le nom de la compétition : ");
				String teamOrNotTeam = InOut.getString("Entrer 'equipe' pour une compétition en équipe ou solo pour compétition individuel (equipe/solo) : ");
				Boolean enEquipe = teamOrNotTeam.equals("equipe");
				try {
					String dateCloture = InOut.getString("Entrer la date de clôture 'yyyy-MM-dd' : ");
					Date localDate = formatter.parse(dateCloture);
					inscriptions.createCompetition(nomCompet, localDate, enEquipe);
				} catch(ParseException e) {
					System.out.println("Veuillez respecter le format de la date 'yyyy-MM-dd' ! " + e);
				}
				System.out.println("La compétition, " + nomCompet + " a était créée avec succés.");
			}
		};
	}
	
	public Option listCompetOption() {
	//(fichier) inscriptions.getCompetitions() équivalent à Passerelle.getData("Competition"); (serveur)
		return new Option("Lister les compétitions", "a",
				() -> {System.out.println(Passerelle.getData("Competition"));}
		);
	}
	
	private List<Competition> selectCompet() {
		return new List<Competition>("Sélectionner une compétition", "e",
				() -> new ArrayList<>(Passerelle.getData("Competition")),
				(element) -> menuSelectCompet(element)
				);
	}
	
	public Menu menuSelectCompet(Competition competition) {
		Menu selectCompet = new Menu("Compétition : " + competition.getNom());
		selectCompet.add(menuAdd(competition));
		selectCompet.add(editNameCompetOption(competition));
		selectCompet.add(menuRemove(competition));
		selectCompet.add(detailCompet(competition));
		selectCompet.add(editDateEnd(competition));
		selectCompet.addBack("b");
		return selectCompet;
	}
	
	private Menu menuAdd(Competition competition) {
		Menu menuAdd = new Menu("Menu Inscription", "i");
		if(competition.estEnEquipe()) {
			menuAdd.add(addTeamInCompetOption(competition));
		}
		else {
			menuAdd.add(addGuyInCompetOption(competition));
		}
		menuAdd.addBack("b");
		return menuAdd;
	}
	
	public Option addTeamInCompetOption(Competition competition) {
		return new List <Equipe>("Sélection de l'équipe", "e",
				() -> new ArrayList<>(Passerelle.getData("Equipe")),
				(element) -> addTeamInCompet(competition, element)
			);
	}
	
	public Option addTeamInCompet(Competition competition, Equipe equipe) {
		return new Option ("Inscrire " + equipe.getNom() + " dans la compétition : " + competition.getNom(), "a",
				() -> {
						try {
								competition.add(equipe);
								System.out.println(equipe.getNom() + " a était inscrit dans la compétition : " + competition.getNom());
						}
						catch(RuntimeException e) {
							System.out.println("La date de clôture est passée " + e);
						}
					}
				);
	}
	
	public Option addGuyInCompetOption(Competition competition) {
		return new List <Personne>("Sélection du sportif", "s",
				() -> new ArrayList<>(Passerelle.getData("Personne")),
				(element) -> addGuyInCompet(competition, element) 
			);
	}
	
	public Option addGuyInCompet(Competition competition, Personne personne) {
		return new Option ("Inscrire " + personne.getNom() + " " + personne.getPrenom() + " dans la compétition : " + competition.getNom(), "a",
				() -> {
					try {
						competition.add(personne); 
						System.out.println(personne.getNom() + " " + personne.getPrenom() + " a était inscrit dans la compétition : " + competition.getNom());
					}
					catch(RuntimeException e) {
						System.out.println("La date de clôture est passée " + e);
					}
				}
				);
	}
	
	public Option editNameCompetOption(Competition competition) {
		return new Option("Renommer une compétition", "r", 
				() -> {String newName = InOut.getString("Entrer le nouveau nom de la compétition : "); competition.setNom(newName); System.out.println("La compétition à bien était renommé");}
				);
	}
	
	private Menu menuRemove(Competition competition) {
		Menu menuRemove = new Menu("Menu Suppression", "s");
		menuRemove.add(removeCompetOption(competition));
		menuRemove.add(removeGuyOrTeamOfCompetOption(competition));
		menuRemove.addBack("b");
		return menuRemove;
	}
	
	public Option removeCompetOption(Competition competition) {
		return new Option("Supprimer la compétition : " + competition.getNom(), "r",
				() -> {
					if(competition.getCandidats().isEmpty()){
						competition.delete();
						System.out.println("La competition a bien était supprimée");
					}
					else {
						System.out.println("La compétition n'est pas vide ! Veuillez supprimer les candidats incrits dans la compétition !");
					}
				}
				);
	}

	public Option removeGuyOrTeamOfCompetOption(Competition competition) {
		return new List<Candidat>("Selectionner l'équipe ou le sportif a supprimer de la compétition : " + competition.getNom(), "d",
				() -> new ArrayList<>(competition.getCandidats()),
				(element) -> removeGuyOrTeamOfCompetAction(competition, element)
				);
	}

	private Option removeGuyOrTeamOfCompetAction(Competition competition, Candidat candidat) {
		return new Option("Supprimer " + candidat.getNom() + " de " + competition.getNom(), "s",
				() -> {competition.remove(candidat); System.out.println(candidat.getNom() + " a bien était supprimé(e) de la compétition : " + competition.getNom());}
				);
	}
	
	public Option detailCompet(Competition competition) {
		return new Option("Informations détaillées sur la compétition : " + competition.getNom(), "t",
				() -> {
					if(competition.estEnEquipe()) {
						System.out.println("\nCompétition : " + competition.getNom() + "\n Date de clôture : " + competition.getDateCloture() + "\n Type : Equipe \n ");
					}
					else {
						System.out.println("\nCompétition : " + competition.getNom() + "\n Date de clôture : " + competition.getDateCloture() + "\n Type : Individuel \n ");
					}
				}
				);
	}
	
	public Option editDateEnd(Competition competition) {
		return new Option("Repousser la date de clôture", "d",
				() -> {
					
					try {
						String dateCloture = InOut.getString("Entrer la nouvelle date de clôture 'yyyy-MM-dd' : ");
						Date localDate = formatter.parse(dateCloture);
						competition.setDateCloture(localDate);
					} catch(ParseException | RuntimeException e) {
						System.out.println("Le format de la date de clôture n'est pas respecté 'yyyy-MM-dd' ou la date de clôture entrée est antérieur à la date d'aujourd'hui " + e);
					}
				}
				);
	}
	
	private Menu menuEquipe() {
		Menu menuEquipe = new Menu("Menu Equipe", "e");
		menuEquipe.add(createTeamOption());
		menuEquipe.add(listTeamOption());
		menuEquipe.add(selectTeam());
//		menuEquipe.add(autoSaveOption());
		
		menuEquipe.addBack("b");
		return menuEquipe;
	}
	
	public Option createTeamOption() {
		return new Option("Créer une équipe", "c",
				() -> {String nomEquipe = InOut.getString("Entrer le nom de l'équipe : ");
				inscriptions.createEquipe (nomEquipe);
				System.out.println("L'équipe, " + nomEquipe + " a était créée avec succés");}
				);
	}
	
	public Option listTeamOption() {
		return new Option("Lister les équipes", "a",
				() -> {System.out.println(Passerelle.getData("Equipe"));}
		);
	}
	
	private List<Equipe> selectTeam()
	{
		return new List<Equipe>("Sélectionner une équipe", "e", 
				() -> new ArrayList<>(Passerelle.getData("Equipe")),
				(element) -> menuSelectTeam(element)
				);
	}
	
	private Menu menuSelectTeam(Equipe equipe) {
		Menu selectTeam = new Menu("Menu Equipe : " + equipe.getNom());
		selectTeam.add(listMemberTeamOption(equipe));
		selectTeam.add(removeTeamOption(equipe));
		selectTeam.add(editNameTeamOption(equipe));
		selectTeam.add(selectMember(equipe));
		selectTeam.add(selectGuy(equipe));
		selectTeam.addBack("b");
		return selectTeam;
	}
	
	public Option listMemberTeamOption(Equipe equipe) {
		return new Option("Lister les membres de " + equipe.getNom(), "a",
				() -> {System.out.println(equipe.getMembres());}
				);
	}
	
	public Option removeTeamOption(Equipe equipe) {
		return new Option("Supprimer " + equipe.getNom(), "s",
				() -> {equipe.delete();}
				);
	}
	
	public Option editNameTeamOption(Equipe equipe) {
		return new Option("Editer le nom d'une équipe", "z",
				() -> {String newName = InOut.getString("Entrer le nouveau nom de l'équipe : "); equipe.setNom(newName);}
				);
	}
	
	private List<Personne> selectMember(Equipe equipe)
	{
		return new List<Personne>("Selectionner un membre de " + equipe.getNom(), "m",
				() -> new ArrayList<>(equipe.getMembres()),
				(element) -> menuSelectMember(equipe, element)
				);
	}
	
	public Menu menuSelectMember(Equipe equipe, Personne personne) 
	{
		Menu selectMember = new Menu(personne.getNom() + " " + personne.getPrenom());
		selectMember.add(removeGuyOfTeamOption(equipe, personne));
		selectMember.addBack("b");
		return selectMember;
	}
	
	public Option removeGuyOfTeamOption(Equipe equipe, Personne personne) {
		return new Option("Supprimer " + personne.getNom() + " " + personne.getPrenom() + " de " + equipe.getNom(), "s",
				() -> {equipe.remove(personne);}
				);
	}
	
	private List<Personne> selectGuy(Equipe equipe)
	{
		return new List<Personne>("Selectionner une personne a ajouter à " + equipe.getNom(), "p",
				() -> new ArrayList<>(Passerelle.getData("Personne")),
				(element) -> menuSelectGuy(equipe, element)
				);
	}
	
	public Menu menuSelectGuy(Equipe equipe, Personne personne) 
	{
		Menu selectGuy = new Menu(personne.getNom() + " " + personne.getPrenom());
		selectGuy.add(addGuyInTeamOption(equipe, personne));
		selectGuy.addBack("b");
		return selectGuy;
	}
	
	public Option addGuyInTeamOption(Equipe equipe, Personne personne) {
		return new Option("Ajouter un sportif dans une équipe", "r",
				() -> {equipe.add(personne);}
		);
	}
	
	private Menu menuPersonne() {
		Menu menuPersonne = new Menu("Menu Personne", "p");
		menuPersonne.add(addAGuyOption());
		menuPersonne.add(listGuysOption());
		menuPersonne.add(selectGuys());
//		menuPersonne.add(autoSaveOption());
		menuPersonne.addBack("b");
		return menuPersonne;
	}
	
	public Option addAGuyOption() {
		
		return new Option("Ajouter un sportif", "c",
				() -> {
					String nomPersonne = InOut.getString("Nom : ");
					String prenomPersonne = InOut.getString("Prenom : ");
					String mailPersonne = InOut.getString("Mail : ");
					Personne createdGuy = inscriptions.createPersonne(nomPersonne, prenomPersonne, mailPersonne);
					Passerelle.save(createdGuy);
					System.out.println(createdGuy.getNom() + " " + createdGuy.getPrenom() + ", a était créé(e) avec succés" + " son mail est : " + createdGuy.getMail());
				}
				);
	}

	public Option listGuysOption() {
		
		return new Option("Lister les sportifs", "a",
				() -> {System.out.println(Passerelle.getData("Personne"));}
		);
	}
	
	private List<Personne> selectGuys()
	{
		return new List<Personne>("Sélectionner une personne", "e",
				() -> new ArrayList<>(Passerelle.getData("Personne")),
				(element) -> menuSelectGuy(element)
				);
	}
	
	private Menu menuSelectGuy(Personne personne) {
		Menu selectGuy = new Menu(personne.getNom() + " " + personne.getPrenom());
		selectGuy.add(menuEditGuy(personne));
		selectGuy.add(removeGuyOption(personne));
		selectGuy.addBack("b");
		return selectGuy;
	}
	
	public Option removeGuyOption(Personne personne) {
		return new Option("Supprimer " + personne.getNom() + " " + personne.getPrenom(), "s", 
				() -> {personne.delete();}
				);
	}
	
	public Menu menuEditGuy(Personne personne) {
		Menu menuEditGuy = new Menu ("Menu édition", "e");
		menuEditGuy.add(editNameOption(personne));
		menuEditGuy.add(editLastNameOption(personne));
		menuEditGuy.add(editMailOption(personne));
		menuEditGuy.addBack("b");
		return menuEditGuy;
	}
	
	public Option editNameOption(Personne personne) {
		return new Option("Editer le nom", "n",
				() -> {String newName = InOut.getString("Entrer le nouveau nom : "); personne.setNom(newName); System.out.println("Le nom a bien été modifié");}
				);
	}
	
	public Option editLastNameOption(Personne personne) {
		return new Option("Editer le prenom", "p",
				() -> {String newName = InOut.getString("Entrer le nouveau prenom : "); personne.setPrenom(newName); System.out.println("Le prenom a bien été modifié");}
				);
	}
	
	public Option editMailOption(Personne personne) {
		return new Option("Editer le mail", "m",
				() -> {String newMail = InOut.getString("Entrer le nouveaul mail de " + personne.getNom() + " " + personne.getPrenom() + " : "); personne.setMail(newMail); System.out.println("Le mail de " + personne.getNom() + " " + personne.getPrenom() + " a bien été changé");}
				);
	}
}