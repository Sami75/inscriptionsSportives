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
//		menuCompetition.add(addTeamInCompetOption());
//		menuCompetition.add(addGuyInCompetOption());
//		menuCompetition.add(editNameCompetOption());
//		menuCompetition.add(menuRemove());
		menuCompetition.add(selectCompet());
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
		selectCompet.add(menuRemove(competition));
		selectCompet.addBack("b");
		return selectCompet;
	}
	
	public Option addTeamInCompetOption() {
		return new Option("Ajouter une équipe dans une compétition", "3", addTeamInCompetAction());
	}
	
	private Action addTeamInCompetAction() {
		return new Action () {
			public void optionSelected() {
				
				String nameCompet = InOut.getString("Entrer le nom de la compétition : ");
				SortedSet <Candidat> listTeams = inscriptions.getCandidats();
				SortedSet <Competition> listCompet = inscriptions.getCompetitions();
				SortedSet <Equipe> listTeam = inscriptions.getEquipes();
				String nameTeam = null;
				
				do {
				
					for(Competition co : listCompet) {
						if(co.getNom().equals(nameCompet)) {
							nameTeam = InOut.getString("Entrer le nom d'une équipe : ");
							for(Candidat c : listTeams) {
								if(c.getNom().equals(nameTeam)) {
									for(Equipe t : listTeam) {
										if(t.toString().equals(c.toString())) {
											co.add(t);
											System.out.println(nameTeam + "est inscrite dans la compétition " + nameCompet);
										}
									}
								}
							}
						}
					}
				
				}while(!nameTeam.equals("stop"));
				
			}
		};
	}
	
	public Option addGuyInCompetOption() {
		return new Option("Ajouter un sportif dans une compétition", "4", addGuyInCompetAction());
	}
	
	private Action addGuyInCompetAction() {
		return new Action () {
			public void optionSelected() {
				String nameCompet = InOut.getString("Entrer le nom de la compétition : ");
				SortedSet <Candidat> listTeams = inscriptions.getCandidats();
				SortedSet <Competition> listCompet = inscriptions.getCompetitions();
				SortedSet <Personne> listGuys = inscriptions.getPersonnes();
				String nameGuy = null;
				
				
				do {
				
					for(Competition co : listCompet) {
						if(co.getNom().equals(nameCompet)) {
							nameGuy = InOut.getString("Entrer le nom d'un sportif : ");
							for(Candidat c : listTeams) {
								if(c.getNom().equals(nameGuy)) {
									for(Personne p : listGuys) {
										if(p.getNom().equals(nameGuy)) {
											co.add(p);
											System.out.println(nameGuy + "est inscrite dans la compétition " + nameCompet);
										}
									}
								}
							}
						}
					}
				
				}while(!nameGuy.equals("stop"));
			}
		};
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
	
	public Option editNameCompetOption() {
		return new Option("Editer le nom d'une compétition", "5", editNameCompetAction());
	}
	
	private Action editNameCompetAction() {
		return new Action() {
			public void optionSelected() {
				SortedSet <Competition> listCompets = inscriptions.getCompetitions();
				String nameCompet = InOut.getString("Enter le nom de la compétition : ");
				
				for(Competition co : listCompets) {
					if(co.getNom().equals(nameCompet)) {
						String newName = InOut.getString("Entrer le nouveau nom : ");
						co.setNom(newName);
						break;
					}
					else {
						System.out.println("Il y a eu une erreur lors du changement de nom, la compétition n'existe pas.");
						break;
					}
				}
			
			}
		};
	}
	
	private Menu menuRemove(Competition competition) {
		Menu menuRemove = new Menu("Menu Suppression", "6");
		menuRemove.add(removeCompetOption());
		menuRemove.add(removeGuyOrTeamOfCompetOption());
		menuRemove.addBack("b");
		return menuRemove;
	}
	
	public Option removeCompetOption() {
		return new Option("Supprimer une compétition", "1", removeCompetAction());
	}

	private Action removeCompetAction() {
		return new Action() {
			public void optionSelected() {
				SortedSet <Competition> listCompets = inscriptions.getCompetitions();
				String nameCompet = InOut.getString("Entrer une compétition : ");
				boolean deleteSuccess = false;
				
				for(Competition co : listCompets) {
					if(co.getNom().equals(nameCompet)) {
						co.delete();
						System.out.println(nameCompet +  ", a bien était supprimée.");
						deleteSuccess = true;
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a échoué, car la compétition n'existe pas.");
					
				}
			}		
	
		};
		
	}
	
	public Option removeGuyOrTeamOfCompetOption() {
		return new Option("Supprimer des sportifs ou des équipes d'une compétition", "2", removeGuyOrTeamOfCompetAction());
	}

	private Action removeGuyOrTeamOfCompetAction() {
		return new Action() {
			public void optionSelected() {
				SortedSet <Competition> listCompets = inscriptions.getCompetitions();
				SortedSet <Candidat> listCandidats = inscriptions.getCandidats();
				String nameCompet = InOut.getString("Entrer une compétition : ");
				String nameCandidat = null;
				boolean deleteSuccess = false;
				
				do {
					
					for(Competition co : listCompets) {
						if(co.getNom().equals(nameCompet)) {
							nameCandidat = InOut.getString("Entrer le nom du sportif ou d'une équipe : ");
							for(Candidat c : listCandidats) {
								if(c.getNom().equals(nameCandidat)) {
									co.remove(c);
									System.out.println(nameCandidat +  ", a bien était supprimée.");
									deleteSuccess = true;
									break;
								}
							}
						}
					}
					
					if(!deleteSuccess) {
						System.out.println("La suppression a échoué, car le sportif n'existe pas ou n'est pas inscrit dans la compétition.");			
					}
					
				}while(!nameCandidat.equals("stop"));
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