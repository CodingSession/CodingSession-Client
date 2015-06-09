package businesslogik;

public class Beitrag {
	private CodingSession session;
	private String betreff;
	private String beschreibung;
	private boolean schreibschutz;
	private int id;
	long date;
	
	public Beitrag(CodingSession session, String betreff, String beschreibung,
			boolean schreibschutz) {
		
		this.session = session;
		this.betreff = betreff;
		this.beschreibung = beschreibung;
		this.schreibschutz = schreibschutz;
		date=System.currentTimeMillis();
		id=CommunityFeed.getAnzahl();
	}
	public CodingSession getSession() {
		return session;
	}
	public void setSession(CodingSession session) {
		this.session = session;
	}
	public String getBetreff() {
		return betreff;
	}
	public void setBetreff(String betreff) {
		this.betreff = betreff;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public boolean isSchreibschutz() {
		return schreibschutz;
	}
	public void setSchreibschutz(boolean schreibschutz) {
		this.schreibschutz = schreibschutz;
	}
}
