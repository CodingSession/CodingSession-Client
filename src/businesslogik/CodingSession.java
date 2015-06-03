package businesslogik;

import java.io.Serializable;
import java.util.HashMap;

public class CodingSession implements Serializable {
	private static final long serialVersionUID = 1L;
	// nicht im Diagramm,aber bestimmt wichtig
	private int benutzerId;
	private int id;
	private String titel;
	private HashMap<String, String> daten;
	private boolean speichern;
	private boolean neuerCode = false;
	// Im Moment noch als ein String,sp�ter was besseres
	private String code = "";
	private String netCode = "";
	// Cs nur mit titel und speichern erstellbar
	private Profil[] teilnehmer;
	private int anzahlTeilnehmer = 0;
	KommunikationIncoming comi;
	KommunikationOutgoing como;

	public CodingSession(String titel, boolean speichern,
			KommunikationIncoming comi, KommunikationOutgoing como,
			int benutzerId, int id, Object lock) {
		this.titel = titel;
		this.speichern = speichern;
		this.benutzerId = benutzerId;
		this.id = id;
		this.comi = comi;
		this.como = como;
		como.starteCs("CodingSession" + id);
		comi.bekommeCode("CodingSession" + id, benutzerId);
		new Thread() {
			public void run() {
				while (true) {
					synchronized (lock) {
						System.out.println("In cs von " + benutzerId
								+ " wird gewartet");
						try {
							lock.wait();
							if (!netCode.equals(comi.getCode())&&!comi.getCode().equals(code)) {
								code = netCode = comi.getCode();
								neuerCode = true;
							}
							System.out.println("In cs von " + benutzerId
									+ " wurde aktualisert");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	// Methode die zeitlich aufgrufen wird um den alten code mit dem neuen zu
	// erstetzen
	public synchronized void aktualisiereCode(String text, boolean selbst) {
		code = text;
		if (selbst){
			como.ver�ffentlicheCode(code);
			netCode = text;
			neuerCode = false;
		}
	}

	public boolean hasChanged() {
		return neuerCode;
	}

	public void neuerCodeGUI(String text) {
		if (!text.equals(netCode)) {
			aktualisiereCode(text, true);
		}
	}

	public boolean addTeilnehmer(Profil b) {
		if (anzahlTeilnehmer < 10) {
			teilnehmer[anzahlTeilnehmer++] = b;
			return true;
		}
		return false;
	}

	public void sendeEinladung(int benutzer) {
		como.ladeEin(this.getDaten(), benutzer);
	}

	public HashMap<String, String> getDaten() {
		daten = new HashMap<String, String>();
		daten.put("titel", titel);
		daten.put("benutzerid", String.valueOf(benutzerId));
		daten.put("id", String.valueOf(id));
		daten.put("code", code);
		daten.put("anzahlTeilnehmer", String.valueOf(anzahlTeilnehmer));
		for (int i = 0; i < anzahlTeilnehmer; i++) {
			daten.put("Teilnehmer" + i, teilnehmer[i].toString());
		}
		return daten;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSpeichern() {
		return speichern;
	}

	public void setSpeichern(boolean speichern) {
		this.speichern = speichern;
	}

	public String getCode() {
		neuerCode = false;
		return code;
	}

	public int getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
	}

}