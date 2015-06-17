package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CodingSessionController implements Initializable{

	private CodingSessionModell csmod;

	// Aktueller Code
	private String code = "";

	// Neuester Code aus der JMS, zu �berpr�fungszwecken notwendig
	private String netCode = "";

	// Kommunikation
	private KommunikationIncoming comi;
	private KommunikationOutgoing como;

	// Chat von dieser CS
	private Chat chat;

	@FXML
	private Button btnTest;

	@FXML
	private TextArea txtCodingSession;

	@FXML
	private TextArea txtChatRead;

	@FXML
	private TextArea txtChatWrite;

	public CodingSessionController(CodingSessionModell csmod,
			KommunikationIncoming comi, KommunikationOutgoing como) {
		this.csmod = csmod;
		this.comi = comi;
		this.como = como;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		chat = new Chat(como, comi, "" + csmod.getBenutzerId(), csmod.getId());
		como.starteCs("CodingSession" + csmod.getId());
		comi.bekommeCode("CodingSession" + csmod.getId(), csmod.getBenutzerId());
		new Thread() {
			public void run() {
				while (true) {
					try {
						if (comi.hasChanged()) {
							netCode = comi.getCode();
							if (!netCode.equals(code)) {
								code = netCode;
								txtCodingSession
										.setText(code);
							}
						}
						CodingSessionController.this.neuerCodeGUI(txtCodingSession.getText());
						txtChatRead.setText("");
						for (String text : chat.empfangen()) {
							txtChatRead.appendText(text);
						}
						Thread.sleep(200);
						csmod.setCode(code);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@FXML
	public void txtChatEnterGeklickt(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			chat.senden(txtChatWrite.getText() + "\n");
			txtChatWrite.setText("");
		}
	}

	// Methode die zeitlich aufgrufen wird, um den alten Code mit dem neuen zu
	// ersetzen
	public void aktualisiereCode(String text, boolean selbst) {
		code = text;
		if (selbst) {
			como.veroeffentlicheCode(code);
			netCode = text;
		}
	}
	public void neuerCodeGUI(String text) {
		if (!text.equals(netCode)) {
			aktualisiereCode(text, true);
		}
	}

	public boolean addTeilnehmer(String b) {
		if (csmod.getAnzahlTeilnehmer() < 10) {
			csmod.addTeilnehmer(b);
			return true;
		}
		return false;
	}

	public void sendeEinladung(int benutzer) {
		como.ladeEin(csmod, benutzer);
	}

}