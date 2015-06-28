
package businesslogik;

import javax.persistence.*;


@Entity
public class BenutzerkontoRealname extends BenutzerkontoOriginal{
	/**
	 * 
	 */
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String vorname;
	private String nachname;
	
	//Default Konstruktor f�r JPA notwendig
	public BenutzerkontoRealname(){
		
	}
	
	public BenutzerkontoRealname(String email, String pw, String vor, String nach,int id) {
		super(email,pw,id);
		this.vorname = vor;
		this.nachname = nach;
	}
	
	@Override
	public String getName() {
		return vorname + " " + nachname;
	}
	
	@Override
	public void setVorname(String vorname) {
		this.vorname = vorname;
		
	}
	
	@Override
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	@Override
	public void setNickname(String nickname) {
	
	}	
}
