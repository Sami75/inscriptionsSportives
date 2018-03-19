package inscriptions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.SortedSet;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import java.util.ArrayList;

import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

public class InscriptionsSportiveConsole {

	private static Inscriptions inscriptions;
	private Personne createdGuy;
	private Competition createdCompet;
	private Equipe createdTeam;
	final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public InscriptionsSportiveConsole(Inscriptions inscriptions) {
		InscriptionsSportiveConsole.inscriptions = inscriptions;
	}
	
	public Option autoSaveOption() {
		return new Option("Sauvegarder", "x", autoSaveAction());
	}
	
	private Action autoSaveAction() {
		return new Action() {
			public void optionSelected() {
				try
				{
					inscriptions.sauvegarder();
				} 
				catch (IOException e)
				{
					System.out.println("Sauvegarde impossible." + e);
				}
			}
		};
	}
	
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
		menuCompetition.add(autoSaveOption());
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
				String dateCloture = InOut.getString("Entrer la date de clôture : ");
				final LocalDate localDate = LocalDate.parse(dateCloture, DATE_FORMAT);
				
				String teamOrNotTeam = null;
				boolean enEquipe = false;
				
				do {
					teamOrNotTeam = InOut.getString("Entrer 'equipe' pour une compétition en équipe ou solo pour compétition individuel ? (equipe/solo) ");
				}while((teamOrNotTeam.equals("equipe")) && (teamOrNotTeam.equals("solo")));	
				
				enEquipe = teamOrNotTeam.equals("equipe");
				
				Competition createdCompet = inscriptions.createCompetition(nomCompet, localDate, enEquipe);
				System.out.println("La compétition, " + nomCompet + " a était créée avec succés");
			}
		};
	}
	
	public Option listCompetOption() {
		return new Option("Lister les compétitions", "a", listCompetAction());
	}
	
	private Action listCompetAction() {
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getCompetitions());
			}
		};
	}
	
	private List<Competition> selectCompet() {
		return new List<Competition>("Sélectionner une compétition", "e",
				() -> new ArrayList<>(inscriptions.getCompetitions()),
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
		menuAdd.add(addTeamInCompetOption(competition));
		menuAdd.add(addGuyInCompetOption(competition));
		menuAdd.addBack("b");
		return menuAdd;
	}
	
	public Option addTeamInCompetOption(Competition competition) {
		return new List <Equipe>("Sélection de l'équipe", "e",
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> addTeamInCompet(competition, element) 
			);
	}
	
	public Option addTeamInCompet(Competition competition, Equipe equipe) {
		return new Option ("Inscrire " + equipe.getNom() + " dans la compétition : " + competition.getNom(), "a",
				() -> {competition.add(equipe); System.out.println(equipe.getNom() + " a était inscrit dans la compétition : " + competition.getNom());}
				);
	}
	
	public Option addGuyInCompetOption(Competition competition) {
		return new List <Personne>("Sélection du sportif", "s",
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> addGuyInCompet(competition, element) 
			);
	}
	
	public Option addGuyInCompet(Competition competition, Personne personne) {
		return new Option ("Inscrire " + personne.getNom() + " " + personne.getPrenom() + " dans la compétition : " + competition.getNom(), "a",
				() -> {competition.add(personne); System.out.println(personne.getNom() + " " + personne.getPrenom() + " a était inscrit dans la compétition : " + competition.getNom());}
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
					if(!competition.getCandidats().isEmpty()){
						competition.delete(); System.out.println("La competition a bien était supprimée");
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
		return new Option("Information détaillées sur la compétition : " + competition.getNom(), "t",
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
					String dateCloture = InOut.getString("Entrer la nouvelle date de clôture : ");
					final LocalDate localDate = LocalDate.parse(dateCloture, DATE_FORMAT);
					competition.setDateCloture(localDate);
					System.out.println("La date a bien était repoussée");
				}
				);
	}
	
	private Menu menuEquipe() {
		Menu menuEquipe = new Menu("Menu Equipe", "e");
		menuEquipe.add(createTeamOption());
		menuEquipe.add(listTeamOption());
		menuEquipe.add(selectTeam());
		menuEquipe.add(autoSaveOption());
		
		menuEquipe.addBack("b");
		return menuEquipe;
	}
	
	public Option createTeamOption() {
		return new Option("Créer une équipe", "c", createTeamAction());
	}
	
	private Action createTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nomEquipe = InOut.getString("Entrer le nom de l'équipe : ");
				Equipe createdTeam = inscriptions.createEquipe (nomEquipe);
				System.out.println("L'équipe, " + nomEquipe + " a était créée avec succés");
			}
		};
	}
	
	public Option listTeamOption() {
		return new Option("Lister les équipes", "a", listTeamAction());
	}
	
	private Action listTeamAction() {
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getEquipes());
			}
		};
	}
	
	private List<Equipe> selectTeam()
	{
		return new List<Equipe>("Sélectionner une équipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> menuSelectTeam(element)
				);
	}
	
	private Menu menuSelectTeam(Equipe equipe) {
		Menu selectTeam = new Menu("Menu Equipe : " + equipe.getNom());
		selectTeam.add(listMemberTeamOption(equipe));
		selectTeam.add(removeTeamOption(equipe));
		selectTeam.add(editNameTeamOption(equipe));
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
	
	private List<Personne> selectGuy(Equipe equipe)
	{
		return new List<Personne>("Selectionner un membre de " + equipe.getNom(), "m",
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> menuSelectGuy(equipe, element)
				);
	}
	
	public Menu menuSelectGuy(Equipe equipe, Personne personne) 
	{
		Menu selectGuy = new Menu(personne.getNom() + " " + personne.getPrenom());
		selectGuy.add(removeGuyOfTeamOption(equipe, personne));
		selectGuy.add(addGuyInTeamOption(equipe, personne));
		selectGuy.addBack("b");
		return selectGuy;
	}
	public Option removeGuyOfTeamOption(Equipe equipe, Personne personne) {
		return new Option("Supprimer " + personne.getNom() + " " + personne.getPrenom() + " de " + equipe.getNom(), "s",
				() -> {equipe.remove(personne);}
				);
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
		menuPersonne.add(autoSaveOption());
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
					System.out.println(createdGuy.getNom() + " " + createdGuy.getPrenom() + ", a était créé(e) avec succés" + " son mail est : " + createdGuy.getMail());
				}
				);
	}

	public Option listGuysOption() {
		
		return new Option("Lister les sportifs", "a", listGuysAction());
	}

	private Action listGuysAction() {
		
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getPersonnes());
			}
		};
	}
	
	private List<Personne> selectGuys()
	{
		return new List<Personne>("Sélectionner une personne", "e", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
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