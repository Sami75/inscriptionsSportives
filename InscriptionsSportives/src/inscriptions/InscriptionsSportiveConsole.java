package inscriptions;

import java.awt.List;
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
		menuEquipe.add(
				new Option("Créer une équipe", "1", new Action()
				{	

					@Override
					public void optionSelected() {
						// TODO Auto-generated method stub
						String nomEquipe = InOut.getString("Entrer le nom de l'équipe : ");
						Equipe createdTeam = inscriptions.createEquipe (nomEquipe);
						System.out.println("L'équipe, " + nomEquipe + " a était créée avec succés");
					}
					
				}));		
		
		menuEquipe.addBack("b");
		return menuEquipe;
	}
	
	private Menu menuPersonne() {
		Menu menuPersonne = new Menu("Personne", "3");
		menuPersonne.add(addAGuyOption());
		menuPersonne.add(listGuysOption());
		menuPersonne.add(removeGuyOption());
		menuPersonne.add(menuEditGuy());
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
						deleteSuccess = true;
						break;
					}
				}
				
				if(deleteSuccess) {
					System.out.println(nomPersonne + " " + prenomPersonne + ", a bien était supprimé(e)");
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
		return new Option("Edit le nom", "1", editNameAction());
	}
	
	public Action editNameAction() {
		return new Action
	}
}