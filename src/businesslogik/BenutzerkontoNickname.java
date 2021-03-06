
package businesslogik;

import javax.persistence.*;



@Entity
public class BenutzerkontoNickname extends BenutzerkontoOriginal {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nickname;

	//Default Konstruktor f�r JPA notwendig
	public BenutzerkontoNickname(){
			
	}
		
	public BenutzerkontoNickname(String email, String pw, String name,int id) {
		super(email, pw,id);
		this.nickname = name;
	}
	
	@Override
	public String getName() {
		return nickname;
	}
	
	@Override
	public void setVorname(String vorname) {	
	}
	
	@Override
	public void setNachname(String nachname) {

	}
	
	@Override
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}	
}