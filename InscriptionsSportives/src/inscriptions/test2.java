package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class test2 {
	
	final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	final String input = "01-07-2018";
	final LocalDate localDate = LocalDate.parse(input, DATE_FORMAT);

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Competition foot = inscriptions.createCompetition("Mondial de foot", localDate, false);
	
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty"), 
			boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");	

	@Test
	public void testCandidat() {
		
		
	}

	@Test
	public void testGetNom() {
		
		String res = tony.getNom();
		String res2 = boris.getNom();
		
		assertEquals(res,"Tony");
		assertEquals(res2,"Boris");
	}

	@Test
	public void testSetNom() {
		
		
	}

	@Test
	public void testGetCompetitions() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
