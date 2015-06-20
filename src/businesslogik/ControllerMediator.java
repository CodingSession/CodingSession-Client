package businesslogik;

public class ControllerMediator {
	
	private CodingSessionController codingsession;
	private CommunityFeedController communityfeed;
	private FreundeSucheController freundesuche;
	private HauptfensterController hauptfenster;
	private ProfilbearbeitungController profilbearbeitung;
	private ProfilController profil;
	
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
	
	public void setFreundeSuche(FreundeSucheController freundesuche){
		this.freundesuche = freundesuche;
	}

	public void setProfil(ProfilController profil) {
		this.profil = profil;
	}
	
	public void setProfilbearbeitung(ProfilbearbeitungController profilbearbeitung) {
		this.profilbearbeitung = profilbearbeitung;
	}
	
	public void addCommunityFeed(CodingSessionModell csmod){
		communityfeed.addBeitrag(csmod);
	}
	
	public void beenden(){
		if(codingsession !=null)
			codingsession.beenden();
	}
	
	public void changeCodingSession(){
		codingsession.changeModell(KommunikationIncoming.getEinladung());
	}
	
	public void neueCodingSession(){
		hauptfenster.neueCodingSession();
	}
	
	public void neueFreundeSuche(){
		hauptfenster.neueFreundeSuche();
	}
	
	public void neueProfilbearbeitung(){
		hauptfenster.neueProfilBearbeitung();
	}
	
	public void zurueckZumProfil(){
		hauptfenster.zurueckZumProfil();
	}

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }

}
