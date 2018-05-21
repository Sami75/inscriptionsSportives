package inscriptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import MenuInscriptions.InscriptionsSportiveConsole;
import back.Passerelle;
import commandLineMenus.List;
import ihm.Interface;


/**
 * Point d'entr�e dans l'application, un seul objet de type Inscription
 * permet de g�rer les comp�titions, candidats (de type equipe ou personne)
 * ainsi que d'inscrire des candidats � des comp�tition.
 */

public class Inscriptions implements Serializable
{
	private static final long serialVersionUID = -3095339436048473524L;
	private static final String FILE_NAME = "Inscriptions.srz";
	private static Inscriptions inscriptions;
	
	private SortedSet<Competition> competitions = new TreeSet<>();
	private SortedSet<Candidat> candidats = new TreeSet<>();

	private Inscriptions(){}
	
	
	/**
	 * Retourne les compétitions.
	 * @return
	 */
	
	public SortedSet<Competition> getCompetitions()
	{
		return Collections.unmodifiableSortedSet(competitions);
	}
	
	/**
	 * Retourne tous les candidats (personnes et équipes confondues).
	 * @return
	 */
	
	public SortedSet<Candidat> getCandidats()
	{
		return Collections.unmodifiableSortedSet(candidats);
	}

	/**
	 * Retourne toutes les personnes.
	 * @return
	 */
	
	public SortedSet<Personne> getPersonnes()
	{
		SortedSet<Personne> personnes = new TreeSet<>();
		for (Candidat c : getCandidats())
			if (c instanceof Personne)
				personnes.add((Personne)c);
		return Collections.unmodifiableSortedSet(personnes);
	}

	/**
	 * Retourne toutes les équipes.
	 * @return
	 */
	
	public SortedSet<Equipe> getEquipes()
	{
		SortedSet<Equipe> equipes = new TreeSet<>();
		for (Candidat c : getCandidats())
			if (c instanceof Equipe)
				equipes.add((Equipe)c);
		return Collections.unmodifiableSortedSet(equipes);
	}
	
	/**
	 * Cr��e une comp�tition. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Competition}.
	 * @param nom
	 * @param localDate
	 * @param enEquipe
	 * @return
	 */
	
	public Competition createCompetition(String nom, Date localDate, boolean enEquipe)
	{
		Competition competition = new Competition(this, nom, localDate, enEquipe);
		Passerelle.save(competition);
		return competition;
	}

	/**
	 * Cr��e une Candidat de type Personne. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Personne}.

	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Personne createPersonne(String nom, String prenom, String mail)
	{
		Personne personne = new Personne(this, nom, prenom, mail);
		Passerelle.save(personne);
		return personne;
	}
	
	/**
	 * Cr��e une Candidat de type �quipe. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Equipe}.
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Equipe createEquipe(String nom)
	{
		Equipe equipe = new Equipe(this, nom);
		Passerelle.save(equipe);
		return equipe;
	}
	
	void remove(Competition competition)
	{
		competition.delete();;
	}
	
	public void remove(Candidat candidat)
	{
		candidat.delete();
	}
	
	public static Inscriptions getInscriptions()
	{
		if (inscriptions == null)
			inscriptions = new Inscriptions();
		return inscriptions;
	}
	
	@Override
	public String toString()
	{
		return "Candidats : " + getCandidats().toString()
			+ "\nCompetitions  " + getCompetitions().toString();
	}
	
	
	public static void main(String[] args)
	{
		new Passerelle();
		Passerelle.open();
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Interface ihm = new Interface(inscriptions);
		InscriptionsSportiveConsole inscriptionsSportiveConsole = new InscriptionsSportiveConsole(inscriptions);
		inscriptionsSportiveConsole.start();
	}
}
