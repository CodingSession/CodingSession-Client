package businesslogik;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ProfilController implements Initializable {

	ProfilbearbeitungController bearbeitung;
	FreundeSucheController suche;
	
	ProfilModell profilModell = new ProfilModell();

	List<BenutzerkontoOriginal> freunde;

	@FXML
	private Label lblBenutzername;

	@FXML
	private Label lblGeschlecht;

	@FXML
	private Label lblGeburtsdatum;

	@FXML
	private Label lblGeburtsort;

	@FXML
	private Label lblWohnort;

	@FXML
	private Label lblAktuellerJob;

	@FXML
	private Label lblProgrammierkenntnisse;

	@FXML
	ListView<Benutzerkonto> listFreunde;

	public ProfilController() {
		freunde = new LinkedList<BenutzerkontoOriginal>();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			profilModell = Datenhaltung.leseProfil(ControllerMediator.getInstance().getBenutzerkonto().getEmail());
		} catch (PersistenzException e) {
			e.printStackTrace();
		}

		lblBenutzername.setText(ControllerMediator.getInstance().getBenutzerkonto().getName());

		if (profilModell.getGeschlecht() == null && profilModell.getGeburtsdatum() == null &&
			profilModell.getGeburtsdatum() == null && profilModell.getWohnort() == null &&
			profilModell.getAktuellerJob() == null && profilModell.getProgrammierkenntnisse() == null) {
			
			lblGeschlecht.setText("");
			lblGeburtsdatum.setText("");
			lblGeburtsort.setText("");
			lblWohnort.setText("");
			lblAktuellerJob.setText("");
			lblProgrammierkenntnisse.setText("");
		} else {
			lblGeschlecht.setText(profilModell.getGeschlecht());
			lblGeburtsdatum.setText(profilModell.getGeburtsdatum());
			lblGeburtsort.setText(profilModell.getGeburtsort());
			lblWohnort.setText(profilModell.getWohnort());
			lblAktuellerJob.setText(profilModell.getAktuellerJob());
			lblProgrammierkenntnisse.setText(profilModell.getProgrammierkenntnisse());
		}

		this.freunde = ControllerMediator.getInstance().getBenutzerkonto().getFreunde();
		ObservableList<Benutzerkonto> items = listFreunde.getItems();
		items.add(new BenutzerkontoNickname("testemail1", "Beispielfreund","Beispielfreund", 3));
		
		for (Benutzerkonto b : freunde) {
			items.add(b);
		}
		
		listFreunde.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 3) {
						ControllerMediator.getInstance().einladen(
								listFreunde.getSelectionModel()
										.getSelectedItem().getEmail());
					}
				}
			}
		});

	}

	@FXML
	public void abmeldenGeklickt(ActionEvent event) {
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}

	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neueCodingSession();
	}

	@FXML
	public void impressumGeklickt(ActionEvent event) {

	}

	@FXML
	public void profilBearbeitenGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neueProfilbearbeitung();
	}

	@FXML
	public void txtSucheFreundeGeklickt(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			ControllerMediator.getInstance().neueFreundeSuche();
		}
	}

}
