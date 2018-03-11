package inscriptions;

import java.awt.List;
import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;

import commandLineMenus.Action;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

public class InscriptionsSportiveConsole {

	private static Inscriptions inscriptions;
	private Personne createdGuy;
	private Competition createdCompet;
	private Equipe createdTeam;
	


	public InscriptionsSportiveConsole(Inscriptions inscriptions) {
		InscriptionsSportiveConsole.inscriptions = inscriptions;
	}
	
	public void autoSave() {
		try
		{
			inscriptions.sauvegarder();
		} 
		catch (IOException e)
		{
			System.out.println("Sauvegarde impossible." + e);
		}
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
		Menu menuCompetition = new Menu("Compétition", "1");
		
		menuCompetition.add(
				new Option("Créer une compétition", "1", new Action()
				{
					@Override
					public void optionSelected() {
						// TODO Auto-generated method stub
						String nomCompet = InOut.getString("Entrer le nom de la compétition : ");
						Competition createdCompet = inscriptions.createCompetition(nomCompet, null, false);
						System.out.println("La compétition, " + nomCompet + " a était créée avec succés");
					}	
				}));
			
			menuCompetition.addBack("b");
			return menuCompetition;
	}
	
	private Menu menuEquipe() {
		Menu menuEquipe = new Menu("Equipe", "2");
		menuEquipe.add(createTeamOption());
		menuEquipe.add(listTeamOption());
		menuEquipe.add(listMemberTeamOption());
		menuEquipe.add(removeTeamOption());
		menuEquipe.add(editNameTeamOption());
		menuEquipe.add(removeGuyOfTeamOption());
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
				autoSave();
			}
		};
	}
	
	public Option listTeamOption() {
		return new Option("Lister les équipes", "2", listTeamAction());
	}
	
	private Action listTeamAction() {
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getEquipes());
			}
		};
	}
	
	public Option listMemberTeamOption() {
		return new Option("Lister les membres d'une équipe", "3", listMemberTeamAction());
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
		return new Option("Supprimer une équipe", "4", removeTeamAction());
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
						autoSave();
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
		return new Option("Editer le nom d'une équipe", "5", editNameTeamAction());
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
						autoSave();
						System.out.println("Le nouveau nom de l'équipe : " + nameTeam + "est : " + newName);
						break;
					}
				}
			}
		};
	}
	
	public Option removeGuyOfTeamOption() {
		return new Option("Supprimer un sportif d'une équipe", "6", removeGuyOfTeamAction());
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
											autoSave();
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
	
	private Menu menuPersonne() {
		Menu menuPersonne = new Menu("Personne", "3");
		menuPersonne.add(addAGuyOption());
		menuPersonne.add(listGuysOption());
		menuPersonne.add(removeGuyOption());
		menuPersonne.add(menuEditGuy());
		menuPersonne.add(addAGuyInTeamOption());
		menuPersonne.addBack("b");
		return menuPersonne;
	}
	
	public Option addAGuyOption() {
		
		return new Option("Ajouter un sportif", "1", addAGuyAction());
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
				autoSave();
			}
		};
	}

	public Option listGuysOption() {
		
		return new Option("Lister les sportifs", "2", listGuysAction());
	}

	private Action listGuysAction() {
		
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getPersonnes());
			}
		};
	}
	
	public Option removeGuyOption() {
		return new Option("Supprimer un sportif", "3", removeGuyAction());
	}
	
	private Action removeGuyAction() {
		return new Action() {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				String nomPersonne = null;
				String prenomPersonne = null;
				boolean deleteSuccess = false;
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						p.delete();
						nomPersonne = p.getNom();
						prenomPersonne = p.getPrenom();
						System.out.println(nomPersonne + " " + prenomPersonne + ", a bien était supprimé(e)");
						autoSave();
						deleteSuccess = true;
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a échoué, car le sportif n'est pas inscrit");
					
				}
					
			}
			
		};
	}
	
	public Menu menuEditGuy() {
		Menu menuEditGuy = new Menu ("Editer un sportif", "4");
		menuEditGuy.add(editNameOption());
		menuEditGuy.add(editLastNameOption());
		menuEditGuy.add(editMailOption());
		menuEditGuy.addBack("b");
		return menuEditGuy;
	}
	
	public Option editNameOption() {
		return new Option("Editer le nom", "1", editNameAction());
	}
	
	public Action editNameAction() {
		return new Action () {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				String nomPersonne = null;
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						nomPersonne = p.getNom();
						String prenomPersonne = p.getPrenom();
						System.out.println("Vous vous apprêtez à modifier : " + nomPersonne + " " + prenomPersonne);
						nomPersonne  = InOut.getString("Entrer le nouveau nom : ");
						String checkEdit = InOut.getString(nomPersonne + " " + prenomPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setNom(nomPersonne);
							System.out.println(nomPersonne + " " + prenomPersonne + ", le nom a bien était modifié");
							autoSave();
							break;
						}
						else {
							break;
						}
					}
				}
			}
		};
	}
	
	public Option editLastNameOption() {
		return new Option("Editer le prenom", "2", editLastNameAction());
	}
	
	public Action editLastNameAction() {
		return new Action () {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				String prenomPersonne = null;
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						prenomPersonne = p.getPrenom();
						String nomPersonne = p.getNom();
						System.out.println("Vous vous apprêtez à modifier : " + nomPersonne + " " + prenomPersonne);
						prenomPersonne  = InOut.getString("Entrer le nouveau prenom : ");
						String checkEdit = InOut.getString(nomPersonne + " " + prenomPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setPrenom(prenomPersonne);
							System.out.println(nomPersonne + " " + prenomPersonne + ", le prenom a bien était modifié");
							autoSave();
							break;
						}
						else {
							break;
						}
					}
				}
			}
		};
	}
	
	public Option editMailOption() {
		return new Option("Editer le mail", "3", editMailAction());
	}
	
	public Action editMailAction() {
		return new Action () {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						String nomPersonne = p.getNom();
						String prenomPersonne = p.getPrenom();
						System.out.println("Vous vous apprêtez le mail de " + nomPersonne + " " + prenomPersonne);
						mailPersonne  = InOut.getString("Entrer le nouveau mail : ");
						String checkEdit = InOut.getString(mailPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setMail(mailPersonne);
							System.out.println("Le mail de : " + nomPersonne + " " + prenomPersonne + "a bien été modifié");
							autoSave();
							break;
						}
						else {
							break;
						}
					}
				}
			}
		};
	}
	
	public Option addAGuyInTeamOption() {
		return new Option("Ajouter un sportif dans une équipe", "5", addAGuyInTeamAction());
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
											autoSave();
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
}