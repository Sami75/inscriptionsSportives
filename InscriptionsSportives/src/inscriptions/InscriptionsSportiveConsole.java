package inscriptions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.SortedSet;
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
		Menu menuCompetition = new Menu("Menu Compétition", "1");
		
		menuCompetition.add(createCompetOption());
		menuCompetition.add(listCompetOption());
		menuCompetition.add(selectCompet());
		menuCompetition.add(autoSaveOption());
		menuCompetition.addBack("b");
		return menuCompetition;
	}
	
	public Option createCompetOption() {
		return new Option("Créer une compétition", "1", createCompetAction());
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
		return new Option("Lister les compétitions", "2", listCompetAction());
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
				() -> {competition.delete(); System.out.println("La competition a bien était supprimée");}
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
	
	private Menu menuEquipe() {
		Menu menuEquipe = new Menu("Menu Equipe", "2");
		menuEquipe.add(createTeamOption());
		menuEquipe.add(menuList());
		menuEquipe.add(removeTeamOption());
		menuEquipe.add(editNameTeamOption());
		menuEquipe.add(removeGuyOfTeamOption());
		menuEquipe.add(addAGuyInTeamOption());
		menuEquipe.addBack("b");
		return menuEquipe;
	}
	
	public Option createTeamOption() {
		return new Option("Créer une équipe", "1", createTeamAction());
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
	
	private Menu menuList() {
		Menu menuList = new Menu("Affichage", "2");
		menuList.add(listTeamOption());
		menuList.add(listMemberTeamOption());
		menuList.addBack("b");
		return menuList;
	}
	
	public Option listTeamOption() {
		return new Option("Lister les équipes", "1", listTeamAction());
	}
	
	private Action listTeamAction() {
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getEquipes());
			}
		};
	}
	
	public Option listMemberTeamOption() {
		return new Option("Lister les membres d'une équipe", "2", listMemberTeamAction());
	}
	
	private Action listMemberTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				SortedSet<Equipe> listTeam = inscriptions.getEquipes();
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					if(c.getNom().equals(nameTeam)) {
						for(Equipe t : listTeam) {
							if(t.toString().equals(c.toString())) {
								System.out.println(t.getMembres());
							}
						}
					}
				}
			}
		};
	}
	
	public Option removeTeamOption() {
		return new Option("Supprimer une équipe", "3", removeTeamAction());
	}
	
	private Action removeTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				boolean deleteSuccess = false;
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					
					if(c.getNom().equals(nameTeam)) {
						c.delete();
						System.out.println(c.getNom() + ", a bien était supprimée");
						deleteSuccess = true;
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a échoué, car l'équipe n'est pas répertoriée");
					
				}
			}
		};
	}
	
	public Option editNameTeamOption() {
		return new Option("Editer le nom d'une équipe", "4", editNameTeamAction());
	}
	
	private Action editNameTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					
					if(c.getNom().equals(nameTeam)) {
						String newName = InOut.getString("Nouveau de l'équipe : ");
						c.setNom(newName);
						System.out.println("Le nouveau nom de l'équipe : " + nameTeam + "est : " + newName);
						break;
					}
				}
			}
		};
	}
	
	public Option removeGuyOfTeamOption() {
		return new Option("Supprimer un sportif d'une équipe", "5", removeGuyOfTeamAction());
	}
	
	private Action removeGuyOfTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				boolean deleteSuccess = false;
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				SortedSet<Equipe> listTeam = inscriptions.getEquipes();
				String nomPersonne = null;
				
				do {
					for(Candidat c : listTeams) {
					
						if(c.getNom().equals(nameTeam)) {
							nomPersonne = InOut.getString("Entrer le nom du sportif à supprimer : ");
							for(Personne p : listGuys) {
								if(p.getNom().equals(nomPersonne)) {
									for(Equipe t : listTeam) {
										if(t.toString().equals(c.toString())) {
											t.remove(p);
											System.out.println(p.getNom() + " " + p.getPrenom() + ", a bien était supprimée de : " + c.getNom());
											deleteSuccess = true;
											break;
										}
									}
								}
							}
						}
					}
				
					if(!deleteSuccess) {
						System.out.println("La suppression a échoué, car le sportif n'appartient pas  n'est pas répertoriée");
					}
				}while(!nomPersonne.equals("stop"));
			}
		};
	}

	public Option addAGuyInTeamOption() {
		return new Option("Ajouter un sportif dans une équipe", "7", addAGuyInTeamAction());
	}
	
	private Action addAGuyInTeamAction() {
		return new Action() {
			public void optionSelected() {
				SortedSet <Candidat> listTeams = inscriptions.getCandidats();
				SortedSet <Equipe> listTeam = inscriptions.getEquipes();
				System.out.println(inscriptions.getEquipes());
				String selectTeam = InOut.getString("Selectionner l'équipe : ");
				String nomPersonne = null;
				SortedSet <Personne> listGuys = inscriptions.getPersonnes();
				System.out.println(inscriptions.getPersonnes());
				
				do {
					nomPersonne = InOut.getString("Nom du sportif : ");
					for(Candidat c : listTeams) {
						if(c.getNom().equals(selectTeam)) {
							for(Equipe t : listTeam) {
								if(t.toString().equals(c.toString())) {
									
									for(Personne p : listGuys) {
										if(p.getNom().equals(nomPersonne)) {
											t.add(p);
											System.out.println("Le sportif : " + p.getNom() + " " + p.getPrenom() + " a rejoint l'équipe " + c.getNom());
										}
									}
								}
								else {
									System.out.println("Il y a eu une erreur lors de l'ajout du sportif dans l'équipe :" + c.getNom());
								}
							}
							
						}
					}	
				}while(!nomPersonne.equals("stop"));
			}
		};
	}
	
	private Menu menuPersonne() {
		Menu menuPersonne = new Menu("Menu Personne", "3");
		menuPersonne.add(addAGuyOption());
		menuPersonne.add(listGuysOption());
		menuPersonne.add(selectGuys());
		menuPersonne.add(autoSaveOption());
		menuPersonne.addBack("b");
		return menuPersonne;
	}
	
	public Option addAGuyOption() {
		
		return new Option("Ajouter un sportif", "c", addAGuyAction());
	}
	
	private Action addAGuyAction() {
		
		return new Action ()
		{
			public void optionSelected()
			{
				String nomPersonne = InOut.getString("Nom : ");
				String prenomPersonne = InOut.getString("Prenom : ");
				String mailPersonne = InOut.getString("Mail : ");
				Personne createdGuy = inscriptions.createPersonne(nomPersonne, prenomPersonne, mailPersonne);
				System.out.println(createdGuy.getNom() + " " + createdGuy.getPrenom() + ", a était créé(e) avec succés" + " son mail est : " + createdGuy.getMail());
			}
		};
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
		return new Option("Editer le nom", "n", editNameAction(personne));
	}
	
	public Action editNameAction(Personne personne) {
		return new Action () {
			public void optionSelected() {
				
				System.out.println("Vous vous apprêtez à modifier : " + personne.getNom() + " " + personne.getPrenom());
				String newName  = InOut.getString("Entrer le nouveau nom : ");
				String checkEdit = InOut.getString(newName + " " + personne.getPrenom() + ", vous validez la modification ? (Y/N) ");

				if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
					personne.setNom(newName);
					System.out.println("Le nom a bien était modifié");
				}
				else {
					System.out.println("Vous avez abandonné l'édition du nom");
				}
			}
		};
	}
	
	public Option editLastNameOption(Personne personne) {
		return new Option("Editer le prenom", "p", editLastNameAction(personne));
	}
	
	public Action editLastNameAction(Personne personne) {
		return new Action () {
			public void optionSelected() {

				System.out.println("Vous vous apprêtez à modifier : " + personne.getNom() + " " + personne.getPrenom());
				String newLastName  = InOut.getString("Entrer le nouveau nom : ");
				String checkEdit = InOut.getString(personne.getNom() + " " + newLastName + ", vous validez la modification ? (Y/N) ");

				if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
					personne.setPrenom(newLastName);
					System.out.println("Le prenom a bien était modifié");
				}
				else {
					System.out.println("Vous avez abandonné l'édition du prénom");
				}
			}
		};
	}
	
	public Option editMailOption(Personne personne) {
		return new Option("Editer le mail", "m", editMailAction(personne));
	}
	
	public Action editMailAction(Personne personne) {
		return new Action () {
			public void optionSelected() {
				System.out.println("Vous vous apprêtezle mail de " + personne.getNom() + " " + personne.getPrenom());
				String newMail  = InOut.getString("Entrer le nouveau mail : ");
				String checkEdit = InOut.getString(newMail + ", vous validez la modification ? (Y/N) ");

				if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
					personne.setMail(newMail);
					System.out.println("Le mail a bien était modifié");
				}
				else {
					System.out.println("Vous avez abandonné l'édition du mail");
				}
			}
		};
	}
}