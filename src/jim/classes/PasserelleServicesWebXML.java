// Projet TraceGPS - API Java
// Fichier : PasserelleServicesWeb.java
// Cette classe hÃ©rite de la classe Passerelle
// Elle fournit des mÃ©thodes pour appeler les diffÃ©rents services web
// Elle utilise le modÃ¨le Jaxp pour parcourir le document XML
// Le modÃ¨le Jaxp fait partie du JDK (et Ã©galement du SDK Android)
// DerniÃ¨re mise Ã  jour : 19/11/2018 par Jim

package jim.classes;

import java.util.ArrayList;
import java.util.Date;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PasserelleServicesWebXML extends PasserelleXML {

	// attributs privÃ©s
	private static String formatDateUS = "yyyy-MM-dd HH:mm:ss";

	// Adresse de l'hÃ©bergeur Internet
	//private static String _adresseHebergeur = "http://sio.lyceedelasalle.fr/tracegps/services/";
	// Adresse du localhost en cas d'exÃ©cution sur le poste de dÃ©veloppement (projet de tests des classes)
	private static String _adresseHebergeur = "http://127.0.0.1/ws-php-dumas/tracegps/services/";

	// Noms des services web dÃ©jÃ  traitÃ©s par la passerelle
	private static String _urlArreterEnregistrementParcours = "ArreterEnregistrementParcours.php";
	private static String _urlChangerDeMdp = "ChangerDeMdp.php";
	private static String _urlConnecter = "Connecter.php";
	private static String _urlCreerUnUtilisateur = "CreerUnUtilisateur.php";
	private static String _urlDemanderMdp = "DemanderMdp.php";
	private static String _urlDemanderUneAutorisation = "DemanderUneAutorisation.php";
	private static String _urlDemarrerEnregistrementParcours = "DemarrerEnregistrementParcours.php";
	private static String _urlEnvoyerPosition = "EnvoyerPosition.php";
	private static String _urlGetLesParcoursDunUtilisateur = "GetLesParcoursDunUtilisateur.php";
	private static String _urlGetLesUtilisateursQueJautorise = "GetLesUtilisateursQueJautorise.php";
	private static String _urlGetLesUtilisateursQuiMautorisent = "GetLesUtilisateursQuiMautorisent.php";
	private static String _urlGetTousLesUtilisateurs = "GetTousLesUtilisateurs.php";
	private static String _urlGetUnParcoursEtSesPoints = "GetUnParcoursEtSesPoints.php";
	private static String _urlRetirerUneAutorisation = "RetirerUneAutorisation.php";
	private static String _urlSupprimerUnUtilisateur = "SupprimerUnUtilisateur.php";
	private static String _urlSupprimerUnParcours = "SupprimerUnParcours.php";

	// -------------------------------------------------------------------------------------------------
	// ------------------------------------- mÃ©thodes dÃ©jÃ  dÃ©veloppÃ©es ---------------------------------
	// -------------------------------------------------------------------------------------------------
	
	// MÃ©thode statique pour se connecter (service Connecter.php)
	// La mÃ©thode doit recevoir 2 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	public static String connecter(String pseudo, String mdpSha1)
	{
		String reponse = "";
		try
		{	// crÃ©ation d'un nouveau document XML Ã  partir de l'URL du service web et des paramÃ¨tres
			String urlDuServiceWeb = _adresseHebergeur + _urlConnecter;
			urlDuServiceWeb += "?pseudo=" + pseudo;
			urlDuServiceWeb += "&mdpSha1=" + mdpSha1;

			// crÃ©ation d'un flux en lecture (InputStream) Ã  partir du service
			InputStream unFluxEnLecture = getFluxEnLecture(urlDuServiceWeb);

			// crÃ©ation d'un objet org.w3c.dom.Document Ã  partir du flux ; il servira Ã  parcourir le flux XML
			Document leDocument = getDocumentXML(unFluxEnLecture);

			// parsing du flux XML
			Element racine = (Element) leDocument.getElementsByTagName("data").item(0);
			reponse = racine.getElementsByTagName("reponse").item(0).getTextContent();

			// retour de la rÃ©ponse du service web
			return reponse;
		}
		catch (Exception ex)
		{	String msg = "Erreur : " + ex.getMessage();
			return msg;
		}
	}
	
	// MÃ©thode statique pour obtenir la liste de tous les utilisateurs de niveau 1 (service GetTousLesUtilisateurs.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    lesUtilisateurs : collection (vide) Ã  remplir Ã  partir des donnÃ©es fournies par le service web
	public static String getTousLesUtilisateurs(String pseudo, String mdpSha1, ArrayList<Utilisateur> lesUtilisateurs)
	{
		String reponse = "";
		try
		{	// crÃ©ation d'un nouveau document XML Ã  partir de l'URL du service web et des paramÃ¨tres
			String urlDuServiceWeb = _adresseHebergeur + _urlGetTousLesUtilisateurs;
			urlDuServiceWeb += "?pseudo=" + pseudo;
			urlDuServiceWeb += "&mdpSha1=" + mdpSha1;

			// crÃ©ation d'un flux en lecture (InputStream) Ã  partir du service
			InputStream unFluxEnLecture = getFluxEnLecture(urlDuServiceWeb);

			// crÃ©ation d'un objet org.w3c.dom.Document Ã  partir du flux ; il servira Ã  parcourir le flux XML
			Document leDocument = getDocumentXML(unFluxEnLecture);

			// parsing du flux XML
			Element racine = (Element) leDocument.getElementsByTagName("data").item(0);
			reponse = racine.getElementsByTagName("reponse").item(0).getTextContent();

			NodeList listeNoeudsUtilisateurs = leDocument.getElementsByTagName("utilisateur");
			/* Exemple de donnÃ©es obtenues pour un utilisateur :
				<utilisateur>
					<id>2</id>
					<pseudo>callisto</pseudo>
					<adrMail>delasalle.sio.eleves@gmail.com</adrMail>
					<numTel>22.33.44.55.66</numTel>
					<niveau>1</niveau>
					<dateCreation>2018-01-19 20:11:24</dateCreation>
					<nbTraces>2</nbTraces>
					<dateDerniereTrace>2018-01-19 13:08:48</dateDerniereTrace>
				</utilisateur>
			 */

			// vider d'abord la collection avant de la remplir
			lesUtilisateurs.clear();

			// parcours de la liste des noeuds <utilisateur> et ajout dans la collection lesUtilisateurs
			for (int i = 0 ; i <= listeNoeudsUtilisateurs.getLength()-1 ; i++)
			{	// crÃ©ation de l'Ã©lÃ©ment courant Ã  chaque tour de boucle
				Element courant = (Element) listeNoeudsUtilisateurs.item(i);

				// lecture des balises intÃ©rieures
				int unId = Integer.parseInt(courant.getElementsByTagName("id").item(0).getTextContent());
				String unPseudo = courant.getElementsByTagName("pseudo").item(0).getTextContent();
				String unMdpSha1 = "";								// par sÃ©curitÃ©, on ne rÃ©cupÃ¨re pas le mot de passe
				String uneAdrMail = courant.getElementsByTagName("adrMail").item(0).getTextContent();
				String unNumTel = courant.getElementsByTagName("numTel").item(0).getTextContent();
				int unNiveau = Integer.parseInt(courant.getElementsByTagName("niveau").item(0).getTextContent());
				Date uneDateCreation = Outils.convertirEnDate(courant.getElementsByTagName("dateCreation").item(0).getTextContent(), formatDateUS);
				int unNbTraces = Integer.parseInt(courant.getElementsByTagName("nbTraces").item(0).getTextContent());
				Date uneDateDerniereTrace = null;
				if (unNbTraces > 0)
					uneDateDerniereTrace = Outils.convertirEnDate(courant.getElementsByTagName("dateDerniereTrace").item(0).getTextContent(), formatDateUS);

				// crÃ©e un objet Utilisateur
				Utilisateur unUtilisateur = new Utilisateur(unId, unPseudo, unMdpSha1, uneAdrMail, unNumTel, unNiveau, uneDateCreation, unNbTraces, uneDateDerniereTrace);

				// ajoute l'utilisateur Ã  la collection lesUtilisateurs
				lesUtilisateurs.add(unUtilisateur);
			}

			// retour de la rÃ©ponse du service web
			return reponse;
		}
		catch (Exception ex)
		{	String msg = "Erreur : " + ex.getMessage();
			return msg;
		}
	}
	
	// MÃ©thode statique pour crÃ©er un utilisateur (service CreerUnUtilisateur.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//   pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//   adrMail : son adresse mail
	//   numTel : son numÃ©ro de tÃ©lÃ©phone
	public static String creerUnUtilisateur(String pseudo, String adrMail, String numTel)
	{
		String reponse = "";
		try
		{	// crÃ©ation d'un nouveau document XML Ã  partir de l'URL du service web et des paramÃ¨tres
			String urlDuServiceWeb = _adresseHebergeur + _urlCreerUnUtilisateur;
			urlDuServiceWeb += "?pseudo=" + pseudo;
			urlDuServiceWeb += "&adrMail=" + adrMail;
			urlDuServiceWeb += "&numTel=" + numTel;

			// crÃ©ation d'un flux en lecture (InputStream) Ã  partir du service
			InputStream unFluxEnLecture = getFluxEnLecture(urlDuServiceWeb);

			// crÃ©ation d'un objet org.w3c.dom.Document Ã  partir du flux ; il servira Ã  parcourir le flux XML
			Document leDocument = getDocumentXML(unFluxEnLecture);

			// parsing du flux XML
			Element racine = (Element) leDocument.getElementsByTagName("data").item(0);
			reponse = racine.getElementsByTagName("reponse").item(0).getTextContent();

			// retour de la rÃ©ponse du service web
			return reponse;
		}
		catch (Exception ex)
		{	String msg = "Erreur : " + ex.getMessage();
			return msg;
		}
	}

	// MÃ©thode statique pour supprimer un utilisateur (service SupprimerUnUtilisateur.php)
	// Ce service permet Ã  un administrateur de supprimer un utilisateur (Ã  condition qu'il ne possÃ¨de aucune trace enregistrÃ©e)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//   pseudo : le pseudo de l'administrateur qui fait appel au service web
	//   mdpSha1 : le mot de passe hashÃ© en sha1
	//   pseudoAsupprimer : le pseudo de l'utilisateur Ã  supprimer
	public static String supprimerUnUtilisateur(String pseudo, String mdpSha1, String pseudoAsupprimer)
	{
		String reponse = "";
		try
		{	// crÃ©ation d'un nouveau document XML Ã  partir de l'URL du service web et des paramÃ¨tres
			String urlDuServiceWeb = _adresseHebergeur + _urlSupprimerUnUtilisateur;
			urlDuServiceWeb += "?pseudo=" + pseudo;
			urlDuServiceWeb += "&mdpSha1=" + mdpSha1;
			urlDuServiceWeb += "&pseudoAsupprimer=" + pseudoAsupprimer;

			// crÃ©ation d'un flux en lecture (InputStream) Ã  partir du service
			InputStream unFluxEnLecture = getFluxEnLecture(urlDuServiceWeb);

			// crÃ©ation d'un objet org.w3c.dom.Document Ã  partir du flux ; il servira Ã  parcourir le flux XML
			Document leDocument = getDocumentXML(unFluxEnLecture);

			// parsing du flux XML
			Element racine = (Element) leDocument.getElementsByTagName("data").item(0);
			reponse = racine.getElementsByTagName("reponse").item(0).getTextContent();

			// retour de la rÃ©ponse du service web
			return reponse;
		}
		catch (Exception ex)
		{	String msg = "Erreur : " + ex.getMessage();
			return msg;
		}
	}

	// MÃ©thode statique pour modifier son mot de passe (service ChangerDeMdp.php)
	// La mÃ©thode doit recevoir 4 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    nouveauMdp : le nouveau mot de passe
	//    confirmationMdp : la confirmation du nouveau mot de passe
	public static String changerDeMdp(String pseudo, String mdpSha1, String nouveauMdp, String confirmationMdp)
	{
		String reponse = "";
		try
		{	// crÃ©ation d'un nouveau document XML Ã  partir de l'URL du service web et des paramÃ¨tres
			String urlDuServiceWeb = _adresseHebergeur + _urlChangerDeMdp;
			urlDuServiceWeb += "?pseudo=" + pseudo;
			urlDuServiceWeb += "&mdpSha1=" + mdpSha1;
			urlDuServiceWeb += "&nouveauMdp=" + nouveauMdp;
			urlDuServiceWeb += "&confirmationMdp=" + confirmationMdp;

			// crÃ©ation d'un flux en lecture (InputStream) Ã  partir du service
			InputStream unFluxEnLecture = getFluxEnLecture(urlDuServiceWeb);

			// crÃ©ation d'un objet org.w3c.dom.Document Ã  partir du flux ; il servira Ã  parcourir le flux XML
			Document leDocument = getDocumentXML(unFluxEnLecture);

			// parsing du flux XML
			Element racine = (Element) leDocument.getElementsByTagName("data").item(0);
			reponse = racine.getElementsByTagName("reponse").item(0).getTextContent();

			// retour de la rÃ©ponse du service web
			return reponse;
		}
		catch (Exception ex)
		{	String msg = "Erreur : " + ex.getMessage();
			return msg;
		}
	}

	// -------------------------------------------------------------------------------------------------
	// --------------------------------- mÃ©thodes restant Ã  dÃ©velopper ---------------------------------
	// -------------------------------------------------------------------------------------------------

	// MÃ©thode statique pour demander un nouveau mot de passe (service DemanderMdp.php)
	// La mÃ©thode doit recevoir 1 paramÃ¨tre :
	//    pseudo : le pseudo de l'utilisateur
	public static String demanderMdp(String pseudo)
	{
		return "";				// METHODE A CREER ET TESTER
	}
	
	// MÃ©thode statique pour obtenir la liste des utilisateurs que j'autorise (service GetLesUtilisateursQueJautorise.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    lesUtilisateurs : collection (vide) Ã  remplir Ã  partir des donnÃ©es fournies par le service web
	public static String getLesUtilisateursQueJautorise(String pseudo, String mdpSha1, ArrayList<Utilisateur> lesUtilisateurs)
	{
		return "";				// METHODE A CREER ET TESTER
	}

	// MÃ©thode statique pour obtenir la liste des utilisateurs qui m'autorisent (service GetLesUtilisateursQuiMautorisent.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    lesUtilisateurs : collection (vide) Ã  remplir Ã  partir des donnÃ©es fournies par le service web
	public static String getLesUtilisateursQuiMautorisent(String pseudo, String mdpSha1, ArrayList<Utilisateur> lesUtilisateurs)
	{
		return "";				// METHODE A CREER ET TESTER
	}

	// MÃ©thode statique pour demander une autorisation (service DemanderUneAutorisation.php)
	// La mÃ©thode doit recevoir 5 paramÃ¨tres :
	//   pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//   mdpSha1 : le mot de passe hashÃ© en sha1
	//   pseudoDestinataire : le pseudo de l'utilisateur Ã  qui on demande l'autorisation
	//   texteMessage : le texte d'un message accompagnant la demande
	//   nomPrenom : le nom et le prÃ©nom du demandeur
	public static String demanderUneAutorisation(String pseudo, String mdpSha1, String pseudoDestinataire, String texteMessage, String nomPrenom)
	{
		return "";				// METHODE A CREER ET TESTER
	}
	
	// MÃ©thode statique pour retirer une autorisation (service RetirerUneAutorisation.php)
	// La mÃ©thode doit recevoir 4 paramÃ¨tres :
	//   pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//   mdpSha1 : le mot de passe hashÃ© en sha1
	//   pseudoARetirer : le pseudo de l'utilisateur Ã  qui on veut retirer l'autorisation
	//   texteMessage : le texte d'un message pour un Ã©ventuel envoi de courriel
	public static String retirerUneAutorisation(String pseudo, String mdpSha1, String pseudoARetirer, String texteMessage)
	{
		return "";				// METHODE A CREER ET TESTER
	}
	
	// MÃ©hode statique pour envoyer la position de l'utilisateur (service EnvoyerPosition.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    lePoint : un objet PointDeTrace (vide) qui permettra de rÃ©cupÃ©rer le numÃ©ro attribuÃ© Ã  partir des donnÃ©es fournies par le service web
	public static String envoyerPosition(String pseudo, String mdpSha1, PointDeTrace lePoint)
	{
		return "";				// METHODE A CREER ET TESTER
	}
	
	// MÃ©thode statique pour obtenir un parcours et la liste de ses points (service GetUnParcoursEtSesPoints.php)
	// La mÃ©thode doit recevoir 4 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    idTrace : l'id de la trace Ã  consulter
	//    laTrace : objet Trace (vide) Ã  remplir Ã  partir des donnÃ©es fournies par le service web
	public static String getUnParcoursEtSesPoints(String pseudo, String mdpSha1, int idTrace, Trace laTrace)
	{
		return "";				// METHODE A CREER ET TESTER
	}
	
	// MÃ©thode statique pour obtenir la liste des parcours d'un utilisateur (service GetLesParcoursDunUtilisateur.php)
	// La mÃ©thode doit recevoir 4 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    idUtilisateur : l'id de l'utilisateur dont on veut la liste des parcours
	//    lesTraces : collection (vide) Ã  remplir Ã  partir des donnÃ©es fournies par le service web
	public static String getLesParcoursDunUtilisateur(String pseudo, String mdpSha1, String pseudoConsulte, ArrayList<Trace> lesTraces)
	{
		return "";				// METHODE A CREER ET TESTER
	}
	
	// MÃ©thode statique pour supprimer un parcours (service SupprimerUnParcours.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//   pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//   mdpSha1 : le mot de passe hashÃ© en sha1
	//   idTrace : l'id de la trace Ã  supprimer
	public static String supprimerUnParcours(String pseudo, String mdpSha1, int idTrace)
	{
		String reponse = "";
		try
		{	// crÃ©ation d'un nouveau document XML Ã  partir de l'URL du service web et des paramÃ¨tres
			String urlDuServiceWeb = _adresseHebergeur + _urlSupprimerUnParcours;
			urlDuServiceWeb += "?pseudo=" + pseudo;
			urlDuServiceWeb += "&mdpSha1=" + mdpSha1;
			urlDuServiceWeb += "&idTrace=" + idTrace;

			// crÃ©ation d'un flux en lecture (InputStream) Ã  partir du service
			InputStream unFluxEnLecture = getFluxEnLecture(urlDuServiceWeb);

			// crÃ©ation d'un objet org.w3c.dom.Document Ã  partir du flux ; il servira Ã  parcourir le flux XML
			Document leDocument = getDocumentXML(unFluxEnLecture);

			// parsing du flux XML
			Element racine = (Element) leDocument.getElementsByTagName("data").item(0);
			reponse = racine.getElementsByTagName("reponse").item(0).getTextContent();

			// retour de la rÃ©ponse du service web
			return reponse;
		}
		catch (Exception ex)
		{	String msg = "Erreur : " + ex.getMessage();
			return msg;
		}
	}
	
	// MÃ©thode statique pour dÃ©marrer l'enregistrement d'un parcours (service DemarrerEnregistrementParcours.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    laTrace : un objet Trace (vide) Ã  remplir Ã  partir des donnÃ©es fournies par le service web
	public static String demarrerEnregistrementParcours(String pseudo, String mdpSha1, Trace laTrace)
	{
		return "";				// METHODE A CREER ET TESTER
	}
		
	// MÃ©thode statique pour terminer l'enregistrement d'un parcours (service ArreterEnregistrementParcours.php)
	// La mÃ©thode doit recevoir 3 paramÃ¨tres :
	//    pseudo : le pseudo de l'utilisateur qui fait appel au service web
	//    mdpSha1 : le mot de passe hashÃ© en sha1
	//    idTrace : l'id de la trace Ã  terminer
	public static String arreterEnregistrementParcours(String pseudo, String mdpSha1, int idTrace)
	{
		return "";				// METHODE A CREER ET TESTER
	}

} // fin de la classe
