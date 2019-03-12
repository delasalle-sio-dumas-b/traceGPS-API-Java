package jim.testsunitaires;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import jim.classes.Outils;
import jim.classes.PasserelleServicesWebXML;
import jim.classes.Point;
import jim.classes.PointDeTrace;
import jim.classes.Trace;
import jim.classes.Utilisateur;

public class PasserelleServiceWebXMLTest {
	
	@Test
	public void testConnecter() {
		String msg = PasserelleServicesWebXML.connecter("admin", "adminnnnnnnn");
		assertEquals("Erreur : authentification incorrecte.", msg);
		
		msg = PasserelleServicesWebXML.connecter("admin", Outils.sha1("mdpadmin"));
		assertEquals("Administrateur authentifié.", msg);
		
		msg = PasserelleServicesWebXML.connecter("europa", Outils.sha1("mdputilisateur"));
		assertEquals("Utilisateur authentifié.", msg);	
	}
		
	@Test
	public void testCreerUnUtilisateur() {
		String msg = PasserelleServicesWebXML.creerUnUtilisateur("jim", "delasalle.sio.eleves@gmail.com", "1122334455");
		assertEquals("Erreur : pseudo trop court (8 car minimum) ou déjà existant.", msg);
		
		msg = PasserelleServicesWebXML.creerUnUtilisateur("turlututu", "delasalle.sio.elevesgmail.com", "1122334455");
		assertEquals("Erreur : adresse mail incorrecte ou déjà existante.", msg);

		msg = PasserelleServicesWebXML.creerUnUtilisateur("turlututu", "delasalle.sio.eleves@gmailcom", "1122334455");
		assertEquals("Erreur : adresse mail incorrecte ou déjà existante.", msg);
		
		msg = PasserelleServicesWebXML.creerUnUtilisateur("turlututu", "delasalle.sio.eleves@gmail.com", "1122334455");
		assertEquals("Erreur : adresse mail incorrecte ou déjà existante.", msg);
		
		msg = PasserelleServicesWebXML.creerUnUtilisateur("turlututu", "delasallesioeleves@gmail.com", "1122334455");
		assertEquals("Enregistrement effectué ; vous allez recevoir un courriel avec votre mot de passe.", msg);
		
		msg = PasserelleServicesWebXML.creerUnUtilisateur("turlututu", "de.la.salle.sio.eleves@gmail.com", "1122334455");
		assertEquals("Erreur : pseudo trop court (8 car minimum) ou déjà existant.", msg);	
	}
	
//	@Test
//	public void testDemanderUneAutorisation() {
//		String msg = PasserelleServicesWebXML.demanderUneAutorisation("europa", Outils.sha1("mdputilisateurrrrrr"), "toto", "", "");
//		assertEquals("Erreur : données incomplètes.", msg);
//
//		msg = PasserelleServicesWebXML.demanderUneAutorisation("europa", Outils.sha1("mdputilisateurrrrrr"), "toto", "coucou", "charles-edouard");
//		assertEquals("Erreur : authentification incorrecte.", msg);
//		
//		msg = PasserelleServicesWebXML.demanderUneAutorisation("europa", Outils.sha1("mdputilisateur"), "toto", "coucou", "charles-edouard");
//		assertEquals("Erreur : pseudo utilisateur inexistant.", msg);
//		
//		msg = PasserelleServicesWebXML.demanderUneAutorisation("europa", Outils.sha1("mdputilisateur"), "galileo", "coucou", "charles-edouard");
//		assertEquals("galileo va recevoir un courriel avec votre demande.", msg);
//	}
	
//	@Test
//	public void testRetirerUneAutorisation() {
//		String msg = PasserelleServicesWebXML.retirerUneAutorisation("europa", Outils.sha1("mdputilisateurrrrrr"), "toto", "coucou");
//		assertEquals("Erreur : authentification incorrecte.", msg);
//		
//		msg = PasserelleServicesWebXML.retirerUneAutorisation("europa", Outils.sha1("mdputilisateur"), "toto", "coucou");
//		assertEquals("Erreur : pseudo utilisateur inexistant.", msg);
//		
//		msg = PasserelleServicesWebXML.retirerUneAutorisation("europa", Outils.sha1("mdputilisateur"), "juno", "coucou");
//		assertEquals("Erreur : l'autorisation n'était pas accordée.", msg);	
//		
//		msg = PasserelleServicesWebXML.retirerUneAutorisation("neon", Outils.sha1("mdputilisateur"), "oxygen", "coucou");
//		assertEquals("Autorisation supprimée ; oxygen va recevoir un courriel de notification.", msg);	
//		
//		msg = PasserelleServicesWebXML.retirerUneAutorisation("neon", Outils.sha1("mdputilisateur"), "photon", "");
//		assertEquals("Autorisation supprimée ; photon va recevoir un courriel de notification.", msg);
//	}
	
//	@Test
//	public void testEnvoyerPosition() throws ParseException {
//			Date laDate = Outils.convertirEnDateHeure("24/01/2018 13:42:21");
//		
//		PointDeTrace lePoint = new PointDeTrace(23, 0, 48.15, -1.68, 50, laDate, 80);
//		String msg = PasserelleServicesWebXML.envoyerPosition("europa", Outils.sha1("mdputilisateurrrrrr"), lePoint);
//		assertEquals("Erreur : authentification incorrecte.", msg);
//		
//		lePoint = new PointDeTrace(2333, 0, 48.15, -1.68, 50, laDate, 80);
//		msg = PasserelleServicesWebXML.envoyerPosition("europa", Outils.sha1("mdputilisateur"), lePoint);
//		assertEquals("Erreur : le numéro de trace n'existe pas.", msg);
//		
//		lePoint = new PointDeTrace(22, 0, 48.15, -1.68, 50, laDate, 80);
//		msg = PasserelleServicesWebXML.envoyerPosition("europa", Outils.sha1("mdputilisateur"), lePoint);
//		assertEquals("Erreur : le numéro de trace ne correspond pas à cet utilisateur.", msg);	
//		
//		lePoint = new PointDeTrace(4, 0, 48.15, -1.68, 50, laDate, 80);
//		msg = PasserelleServicesWebXML.envoyerPosition("europa", Outils.sha1("mdputilisateur"), lePoint);
//		assertEquals("Point créé.", msg);
//	}
//
//	@Test
//	public void testDemarrerEnregistrementParcours() {
//		Trace laTrace = new Trace();
//		String msg = PasserelleServicesWebXML.demarrerEnregistrementParcours("europa", Outils.sha1("mdputilisateurrrrrr"), laTrace);
//		assertEquals("Erreur : authentification incorrecte.", msg);
//		
//		laTrace = new Trace();
//		msg = PasserelleServicesWebXML.demarrerEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), laTrace);
//		assertEquals("Trace créée.", msg);	
//	}
//

//	@Test
<<<<<<< HEAD
	public void testArreterEnregistrementParcours() {
		String msg;

		msg = PasserelleServicesWebXML.arreterEnregistrementParcours("europa", Outils.sha1("mdputilisateurrrrrr"), 23);
		assertEquals("Erreur : authentification incorrecte.", msg);
		
		msg = PasserelleServicesWebXML.arreterEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), 230);
		assertEquals("Erreur : parcours inexistant.", msg);
		
		msg = PasserelleServicesWebXML.arreterEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), 5);
		assertEquals("Erreur : le numéro de trace ne correspond pas à cet utilisateur.", msg);
		
		msg = PasserelleServicesWebXML.arreterEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), 4);
		assertEquals("Erreur : cette trace est déjà terminée.", msg);	
		
		msg = PasserelleServicesWebXML.arreterEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), 23);
		assertEquals("Enregistrement terminé.", msg);	
	}
//	@Test
//	public void testSupprimerUnUnParcours() {
//		String msg = PasserelleServicesWebXML.supprimerUnParcours("europa", Outils.sha1("mdputilisateurrrrrr"), 10);
=======
//	public void testDemarrerEnregistrementParcours() {
//		Trace laTrace = new Trace();
//		String msg = PasserelleServicesWebXML.demarrerEnregistrementParcours("europa", Outils.sha1("mdputilisateurrrrrr"), laTrace);
>>>>>>> branch 'master' of https://github.com/delasalle-sio-dumas-b/traceGPS-API-Java.git
//		assertEquals("Erreur : authentification incorrecte.", msg);
//		
//		laTrace = new Trace();
//		msg = PasserelleServicesWebXML.demarrerEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), laTrace);
//		assertEquals("Trace créée.", msg);	
//	}


	@Test
	public void testDemarrerEnregistrementParcours() {
		Trace laTrace = new Trace();
		String msg = PasserelleServicesWebXML.demarrerEnregistrementParcours("europa", Outils.sha1("mdputilisateurrrrrr"), laTrace);
		assertEquals("Erreur : authentification incorrecte.", msg);
		
		laTrace = new Trace();
		msg = PasserelleServicesWebXML.demarrerEnregistrementParcours("europa", Outils.sha1("mdputilisateur"), laTrace);
		assertEquals("Trace créée.", msg);	
	}


	@Test
	public void testArreterEnregistrementParcours() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSupprimerUnUnParcours() {
		String msg = PasserelleServicesWebXML.supprimerUnParcours("europa", Outils.sha1("mdputilisateurrrrrr"), 10);
		assertEquals("Erreur : authentification incorrecte.", msg);
		
		msg = PasserelleServicesWebXML.supprimerUnParcours("europa", Outils.sha1("mdputilisateur"), 100);
		assertEquals("Erreur : parcours inexistant.", msg);
		
		msg = PasserelleServicesWebXML.supprimerUnParcours("europa", Outils.sha1("mdputilisateur"), 22);
		assertEquals("Erreur : vous n'êtes pas le propriétaire de ce parcours.", msg);	
		
		msg = PasserelleServicesWebXML.supprimerUnParcours("europa", Outils.sha1("mdputilisateur"), 30);
		assertEquals("Parcours supprimé.", msg);	
	}	

} // fin du test
