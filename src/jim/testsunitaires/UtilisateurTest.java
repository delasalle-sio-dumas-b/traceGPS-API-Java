package jim.testsunitaires;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import jim.classes.Outils;
import jim.classes.Utilisateur;

public class UtilisateurTest {
	
	private Utilisateur utilisateur1;
	private Utilisateur utilisateur2;
	
	@Before
	public void setUp() throws Exception {
		utilisateur1 = new Utilisateur();
		
		int unId = 111;
		String unPseudo = "toto";
		String unMdpSha1 = "abcdef";
		String uneAdrMail = "toto@free.fr";
		String unNumTel = "1122334455";
		int unNiveau = 1;
		Date uneDateCreation = Outils.convertirEnDateHeure("21/06/2016 14:00:00");
		int unNbTraces = 3;
		Date uneDateDerniereTrace = Outils.convertirEnDateHeure("28/06/2016 14:00:00");
		utilisateur2 = new Utilisateur(unId, unPseudo, unMdpSha1, uneAdrMail, unNumTel, unNiveau, uneDateCreation, unNbTraces, uneDateDerniereTrace);
	}

	@Test
	public void testGetId() {		
		assertEquals("Test getId", 0, utilisateur1.getId());
		assertEquals("Test getId", 111, utilisateur2.getId());
	}

	@Test
	public void testSetId() {
		utilisateur1.setId(5);
		assertEquals("Test setId", 5, utilisateur1.getId());
	}

	@Test
	public void testGetPseudo() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPseudo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMdpSha1() {
		assertEquals("", utilisateur1.getMdpSha1());
		assertEquals("abcdef", utilisateur2.getMdpSha1());
	}

	@Test
	public void testSetMdpSha1() {
		utilisateur1.setMdpSha1("ABCDEFG");
		assertEquals("ABCDEFG", utilisateur1.getMdpSha1());

		utilisateur2.setMdpSha1("132456");
		assertEquals("132456", utilisateur2.getMdpSha1());
	}

	@Test
	public void testGetAdrMail() {
		assertEquals(utilisateur1.getAdrMail(), "");
		assertEquals("toto@free.fr", utilisateur2.getAdrMail());
	}

	@Test
	public void testSetAdrMail() {
		utilisateur1.setAdrMail("abcd@gmail.com");
		assertEquals(utilisateur1.getAdrMail(), "abcd@gmail.com");
		
		utilisateur2.setAdrMail("gigatz@gmail.com");
		assertEquals("gigatz@gmail.com", utilisateur2.getAdrMail());
	}

	@Test
	public void testGetNumTel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNumTel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNiveau() {
		assertEquals("Test getNiveau", 0, utilisateur1.getNiveau());
		assertEquals("Test getNiveau", 1, utilisateur2.getNiveau());
	}

	@Test
	public void testSetNiveau() {
		utilisateur1.setNiveau(2);
		assertEquals("Test setNiveau", 2, utilisateur1.getNiveau());
	}

	@Test
	public void testGetDateCreation() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDateCreation() throws ParseException {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNbTraces() {
		assertEquals("Test getNbTraces", 0, utilisateur1.getNbTraces());
		assertEquals("Test getNbTraces", 3, utilisateur2.getNbTraces());
	}

	@Test
	public void testSetNbTraces() {
		utilisateur1.setNbTraces(10);
		assertEquals("Test setNbTraces", 10, utilisateur1.getNbTraces());
	}

	@Test
	public void testGetDateDerniereTrace() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDateDerniereTrace() throws ParseException {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		String msg = "";
	    msg += "id : " + "0" + "\n";
	    msg += "pseudo : " + "" + "\n";
	    msg += "mdpSha1 : " + "" + "\n";
	    msg += "adrMail : " + "" + "\n";
	    msg += "numTel : " + "" + "\n";
	    msg += "niveau : " + "0" + "\n";
	    msg += "nbTraces : " + "0" + "\n";
	    assertEquals("Test toString", msg, utilisateur1.toString());
	    
		msg = "";
	    msg += "id : " + "111" + "\n";
	    msg += "pseudo : " + "toto" + "\n";
	    msg += "mdpSha1 : " + "abcdef" + "\n";
	    msg += "adrMail : " + "toto@free.fr" + "\n";
	    msg += "numTel : " + "11.22.33.44.55" + "\n";
	    msg += "niveau : " + "1" + "\n";
	    msg += "dateCreation : " + "21/06/2016 14:00:00" + "\n";
	    msg += "nbTraces : " + "3" + "\n";
	    msg += "dateDerniereTrace : " + "28/06/2016 14:00:00" + "\n";
	    assertEquals("Test toString", msg, utilisateur2.toString());
	}

}
