package JUnit;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class TestPersonne {
	
	final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	final String input = "01-07-2018";
	final java.sql.Date localDate = java.sql.Date.valueOf(input);

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Competition foot = inscriptions.createCompetition("Mondial de foot", localDate, false);
	
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty"),
			Martial = inscriptions.createPersonne("Martial", "SuperSub", "Mu@PL.com"),
			Rashford  = inscriptions.createPersonne("Rashford", "Flash", "Mu@PL.com");
	
	@Test
	public void testDelete() {
		
		Equipe ManchesterUnited = inscriptions.createEquipe("Manchester United");
		Set<Equipe> e = new TreeSet<Equipe>();
		e.add(ManchesterUnited);
		ManchesterUnited.add(Martial);
		ManchesterUnited.add(Rashford);
		
		Rashford.delete();
		
		assertEquals("DelEquipes : " + e + " , " + Martial.getEquipes(), e, Martial.getEquipes());
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testPersonne() {
		String nom = tony.getNom();
		String prenom = tony.getPrenom();
		String mail = tony.getMail();
		
		assertEquals("Nom : " + nom, nom, "Tony");
		assertEquals("Prenom : " + prenom, prenom, "Dent de plomb");
		assertEquals("Mail : " + mail, mail, "azerty");
	}

	@Test
	public void testGetPrenom() {
		String prenom = tony.getPrenom();

		assertEquals("Get : " + prenom + " , Dent de plomb", prenom, "Dent de plomb");

	}

	@Test
	public void testSetPrenom() {
		String prenom = "Dent de cuivre";
		
		Personne tony = inscriptions.createPersonne("tony", prenom, "azerty");
		
		assertEquals("Set : " + prenom + " , " + tony.getPrenom(), tony.getPrenom(), prenom);
	}

	@Test
	public void testGetMail() {
		String mail = tony.getMail();
		
		assertEquals("GetMail : " + mail + " , " + tony.getMail(), mail, "azerty");
	}

	@Test
	public void testSetMail() {
		String mail = "1234";
		
		Personne tony = inscriptions.createPersonne("tony", "Dent de cuivre", mail);
		
		assertEquals("SetMail : " + mail + " , " + tony.getMail(), tony.getMail(), mail);
	}

	@Test
	public void testGetEquipes() {
		
		Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
		lesManouches.add(tony);
		Set<Equipe> e = new TreeSet<Equipe>();
		e.add(lesManouches);
		
	    assertEquals("GetEquipe : " + e + " , " + tony.getEquipes(), tony.getEquipes(), e);
	}

	@Test
	public void testAddEquipe() {
		
		Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
		Equipe ManchesterUnited = inscriptions.createEquipe("Manchester United");
		Set<Equipe> e = new TreeSet<Equipe>();
		e.add(lesManouches);
		e.add(ManchesterUnited);
		
		assertEquals("AddEquipes : " + e + " , " + inscriptions.getEquipes(), e, inscriptions.getEquipes());
	}

	@Test
	public void testRemoveEquipe() {
		
		Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
		Equipe ManchesterUnited = inscriptions.createEquipe("Manchester United");
		Set<Equipe> e = new TreeSet<Equipe>();
		e.add(lesManouches);
		e.add(ManchesterUnited);
		e.remove(lesManouches);
		inscriptions.remove(lesManouches);
		
		assertEquals("RemoveEquipes : " + e + " , " + inscriptions.getEquipes(), e, inscriptions.getEquipes());
	}

}
