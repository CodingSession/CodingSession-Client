package businesslogik;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerMediator {

	private CodingSessionController codingsession;
	private CommunityFeedController communityfeed;
	private FreundeSucheController freundesuche;
	private HauptfensterController hauptfenster;
	private ProfilbearbeitungController profilbearbeitung;
	private ProfilController profil;
	private Benutzerkonto benutzerkonto;

	public Benutzerkonto getBenutzerkonto() {
		return benutzerkonto;
	}

	public void setBenutzerkonto(Benutzerkonto benutzerkonto) {
		this.benutzerkonto = benutzerkonto;
	}

	private ControllerMediator() {

	}

	public static ControllerMediator getInstance() {
		return ControllerMediatorHolder.INSTANCE;
	}

	public void setHauptfenster(HauptfensterController hauptfenster) {
		this.hauptfenster = hauptfenster;
	}

	public void setCodingSession(CodingSessionController codingsession) {
		this.codingsession = codingsession;
	}

	public void setCommunityfeed(CommunityFeedController communityfeed) {
		this.communityfeed = communityfeed;
	}

	public void setFreundeSuche(FreundeSucheController freundesuche) {
		this.freundesuche = freundesuche;
	}

	public void setProfil(ProfilController profil) {
		this.profil = profil;
	}

	public void setProfilbearbeitung(ProfilbearbeitungController profilbearbeitung) {
		this.profilbearbeitung = profilbearbeitung;
	}

	public void addCommunityFeed(Beitrag beitrag) {
		communityfeed.addBeitrag(beitrag);
	}
	public void neuerCommunityFee(){
		hauptfenster.neuerCf();
	}
	public void beenden() {
		if (codingsession != null)
			codingsession.beenden();
		hauptfenster.hauptfensterThread.interrupt();
	}

	public void einladungAngenommen() {
		hauptfenster.neueCodingSession(false, KommunikationIncoming.getEinladung());
	}

	public void changeCodingSession(CodingSessionModell cmod) {
		hauptfenster.neueCodingSession(false, cmod);
	}

	public void einladen(String email) {
		codingsession.sendeEinladung(email);
	}

	public void neueCodingSession() {
		hauptfenster.neueCodingSession(true, null);
	}

	public void neueFreundeSuche() {
		hauptfenster.neueFreundeSuche();
	}

	public void neueProfilbearbeitung() {
		hauptfenster.neueProfilBearbeitung();
	}

	public void neuesProfil() {
		hauptfenster.neuesProfil();
	}

	public void schliesseCodingSession() {
		codingsession.killThread();
		hauptfenster.schliesseCodingSession();
	}

	private static class ControllerMediatorHolder {
		private static final ControllerMediator INSTANCE = new ControllerMediator();
	}

}