package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class TestPersonne {
	
	final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	final String input = "01-07-2018";
	final LocalDate localDate = LocalDate.parse(input, DATE_FORMAT);

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Competition flechettes = inscriptions.createCompetition("Mondial de fléchettes", localDate, false);
	
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");

	@Test
	public void testDelete() {
		fail("Not yet implemented");
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
		
		assertEquals(nom, "tony");
		assertEquals(prenom, "Dent de cuivre");
		assertEquals(mail, "azerty");
	}

	@Test
	public void testGetPrenom() {
		String prenom = tony.getPrenom();

		assertEquals(prenom, "Dent de cuivre");
		
		System.out.println("Get : " + prenom + " , " + tony.getPrenom());
	}

	@Test
	public void testSetPrenom() {
		String prenom = "Dent de cuivre";
		
		Personne tony = inscriptions.createPersonne("tony", prenom, "azerty");
		
		assertEquals(tony.getPrenom(), prenom);
		
		System.out.println("Set : " + prenom + " , " + tony.getPrenom());
		
		
	}

	@Test
	public void testGetMail() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMail() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEquipes() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddEquipe() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveEquipe() {
		fail("Not yet implemented");
	}

}
