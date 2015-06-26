package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class ProfilbearbeitungController implements Initializable{

	//Regulaere Ausdruecke fuer die Eingabevalidierung
	private final String vornameRegex = "^[a-zA-Z]{3,20}$";
	private final String nachnameRegex = "^[a-zA-Z]{3,20}$";
	private final String nicknameRegex = "^[a-zA-Z][\\w_-]{3,25}$";
	private final String geburtsdatumRegex = "^([01][0-9]).([01][0-9]).([12][089][0-9][0-9])$";
	private final String geburtsortRegex= "^[a-zA-Z-\\s]{3,20}$";
	private final String wohnortRegex = "^[a-zA-Z-\\s]{3,20}$";
	private final String aktuellerJobRegex = "^[a-zA-Z\\s]{3,100}$";
	private final String pkenntnisseRegex = "^[a-zA-Z0-9#+-/.,!\\s]{3,100}$";
	private final String passwortRegex = "^[a-zA-Z0-9!�$%&/()=?@#^+-_*~'\"\\s]{8,25}$";
	
	ProfilModell profilModell = new ProfilModell();
	Benutzerkonto benutzerkonto = ControllerMediator.getInstance().getBenutzerkonto();
	
	@FXML
	private HBox hboxVorname;
	
	@FXML
	private HBox hboxNachname;
	
	@FXML
	private HBox hboxNickname;
	
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@FXML
	private TextField txtVorname;
	
	@FXML
	private TextField txtNachname;
	
	@FXML
	private TextField txtNickname;
	
	@FXML
	private TextField txtGeburtsdatum;
	
	@FXML
	private TextField txtGeburtsort;
	
	@FXML
	private TextField txtWohnort;
	
	@FXML
	private TextField txtAktuellerJob;
	
	@FXML
	private TextField txtProgrammierkenntnisse;
	
	@FXML
	private PasswordField pwdAltesPasswort;
	
	@FXML
	private PasswordField pwdNeuesPasswort;
	
	@FXML
	private PasswordField pwdPasswortBestaetigung;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		hboxVorname.managedProperty().bind(hboxVorname.visibleProperty());
		hboxNachname.managedProperty().bind(hboxNachname.visibleProperty());
		hboxNickname.managedProperty().bind(hboxNickname.visibleProperty());
		choiceBox.setItems(FXCollections.observableArrayList("Weiblich", "M�nnlich"));
		choiceBox.setTooltip(new Tooltip("W�hle dein Geschlecht"));
		
		if(benutzerkonto instanceof BenutzerkontoRealname) {
			hboxNickname.setVisible(false);
			hboxVorname.setVisible(true);
			hboxNachname.setVisible(true);
		} else {
			hboxNickname.setVisible(true);
			hboxVorname.setVisible(false);
			hboxNachname.setVisible(false);
		}
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}
	
	//Wenn Zeit uebrig bleibt, mach ich das noch schoener.
	@FXML
	public void aenderungenSpeichernGeklickt(ActionEvent event){
		try {
			profilModell = Datenhaltung.leseProfil(benutzerkonto.getEmail());
		} catch (PersistenzException e) {
			e.printStackTrace();
		}
		
		if(benutzerkonto instanceof BenutzerkontoRealname) {
			if(!istLeer(txtVorname.getText())) {
				if(txtVorname.getText().matches(vornameRegex)) {
					benutzerkonto.setVorname(txtVorname.getText());
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiger Vorname", 
							"Bitte gebe einen g�ltigen Vornamen ein!");
				}
			}
			
			if(!istLeer(txtNachname.getText())) {
				if(txtNachname.getText().matches(nachnameRegex)) {
					benutzerkonto.setNachname(txtNachname.getText());
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiger Nachname", 
							"Bitte gebe einen g�ltigen Nachnamen ein!");
				}
			}
		} else {
			if(!istLeer(txtNickname.getText())) {
				if(txtNickname.getText().matches(nicknameRegex)) {
					benutzerkonto.setNickname(txtNickname.getText());
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiger Nickname", 
							"Bitte gebe einen g�ltigen Nicknamen ein!");
				}
			}
		}
		
		if(choiceBox.getValue() != null){
			profilModell.setGeschlecht(choiceBox.getValue());
		}

		if(!istLeer(txtGeburtsdatum.getText())) {
			if(txtGeburtsdatum.getText().matches(geburtsdatumRegex)) {
				profilModell.setGeburtsdatum(txtGeburtsdatum.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiges Geburtsdatum", 
						"Bitte gebe ein g�ltiges Geburtsdatum ein!");
			}	
		}
		
		if(!istLeer(txtGeburtsort.getText())) {
			if(txtGeburtsort.getText().matches(geburtsortRegex)) {
				profilModell.setGeburtsort(txtGeburtsort.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiger Geburtsort",
						"Bitte gebe einen g�ltigen Geburtsort ein!");
			}
		}
		
		if(!istLeer(txtWohnort.getText())) {
			if(txtWohnort.getText().matches(wohnortRegex)) {
				profilModell.setWohnort(txtWohnort.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiger Wohnort", 
						"Bitte gebe einen g�ltigen Wohnort ein!");
			}
		}
		
		if(!istLeer(txtAktuellerJob.getText())) {
			if(txtAktuellerJob.getText().matches(aktuellerJobRegex)) {
				profilModell.setAktuellerJob(txtAktuellerJob.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiger aktueller Job",
						"Bitte gebe einen g�ltigen aktuellen Job ein!");
			}		
		}

		if(!istLeer(txtProgrammierkenntnisse.getText())) {
			if(txtProgrammierkenntnisse.getText().matches(pkenntnisseRegex)) {
				profilModell.setProgrammierkenntnisse(txtProgrammierkenntnisse.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltige Programmierkenntnisse", 
						"Bitte gebe nur g�ltige Programmiersprachen ein!");
			}
			
		}
		
		if(!istLeer(pwdAltesPasswort.getText()) && !istLeer(pwdNeuesPasswort.getText()) && 
		   !istLeer(pwdPasswortBestaetigung.getText())) {
			if(benutzerkonto.getPasswort().equals(pwdAltesPasswort.getText())) {
				if(pwdNeuesPasswort.getText().matches(passwortRegex) && 
				   pwdPasswortBestaetigung.getText().matches(passwortRegex)) {
					if(pwdNeuesPasswort.getText().equals(pwdPasswortBestaetigung.getText())) {
						benutzerkonto.setPasswort(pwdNeuesPasswort.getText());
					} else {
						new CodingSessionDialog().erstelleFehlermeldungDialog("Passwort Widerspruch", 
								"Die eingegebenen neuen Passw�rter stimmen nicht �berein!");
					}
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiges Passwort", 
							"Bitte gebe ein g�ltiges Passwort ein!");
				}		
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ung�ltiges Passwort", 
						"Das alte Passwort stimmt nicht mit dem des Benutzers �berein!");
			}
		}
		
		try {
			Datenhaltung.updateDB((BenutzerkontoOriginal) benutzerkonto);
			Datenhaltung.updateProfil(profilModell);
		} catch (PersistenzException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void zurueckZumProfilGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neuesProfil();
	}
	
	private boolean istLeer(String text){
		if(text.equals(null) || text.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
