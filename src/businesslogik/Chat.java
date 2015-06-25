package businesslogik;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

@Entity
@Table(name = "Chat")
public class Chat {
	// Mit Transient makierte Attribute werden nicht persistert
	@Id
	private int id;
	@ElementCollection
	private List<String> verlauf;
	@Transient
	private int size = 0;
	private String sender;
	@Transient
	private KommunikationOutgoing kommunikationOut;
	@Transient
	private KommunikationIncoming kommunikationIn;

	public Chat() {
		this.verlauf = new LinkedList<String>();
	}

	public Chat(KommunikationOutgoing kommunikationOut,
			KommunikationIncoming kommunikationIn, String sender, int id) {
		this.id = id;
		this.verlauf = new LinkedList<String>();
		this.kommunikationOut = kommunikationOut;
		this.kommunikationIn = kommunikationIn;
		this.sender = sender;
		kommunikationOut.starteChat("Chat" + id);
		kommunikationIn.bekommeChat(id, verlauf);
	}

	public void senden(String nachricht) {
		kommunikationOut.veroeffentlicheChat(nachricht, sender);
	}

	public List<String> empfangen() {
		return this.verlauf;
	}
	
	public void speichern(){
			try {
				Datenhaltung.schreibeChat(this);
			} catch (PersistenzException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setVerlauf(List<String> verlauf) {
		this.verlauf = verlauf;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getId() {
		return this.id;
	}

}
