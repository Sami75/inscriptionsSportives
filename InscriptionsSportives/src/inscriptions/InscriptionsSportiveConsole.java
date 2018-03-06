package inscriptions;

import commandLineMenus.Action;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

public class InscriptionsSportiveConsole {

	private static Inscriptions inscriptions;


	public InscriptionsSportiveConsole(Inscriptions inscriptions) {
		this.inscriptions = inscriptions;
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
		menuPersonne.add(addAGuy());
		menuPersonne.add(listGuys());
		menuPersonne.addBack("b");
		return menuPersonne;
	}
	
	public Option addAGuy() {
		
		return new Option("Ajouter un sportif", "1", () -> {
			String nomPersonne = InOut.getString("Nom : ");
			String prenomPersonne = InOut.getString("Prenom : ");
//			String adressePersonne = InOut.getString("Adresse : ");
//			String villePersonne = InOut.getString("Ville : ");
			String mailPersonne = InOut.getString("Mail : ");
			Personne createdGuy = inscriptions.createPersonne(nomPersonne, prenomPersonne, mailPersonne);
		
			System.out.println(nomPersonne + " " + prenomPersonne + ", a était créé(e) avec succés");
		});
	}
	
	public Option listGuys() {
		String newline = System.getProperty("line.seperator");
		
		return new Option("Lister les sportifs", "2", () -> {
			System.out.println("Sportifs Inscrits : " + newline + inscriptions.getPersonnes());
		});
	}
	
}
