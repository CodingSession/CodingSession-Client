package Businesslogik;
//hier wird sobald jms l�uft sachen passiern
//jetzt erstmal ziemlich theroitsche methoden
	
public class Kommunikation {
	public Object test; 
	public Kommunikation(){
		this.test=new Object();
		//Verbindung mit JMS
	}
	public void ver�ffentlichCode(String code){
		//code an topic geschrieben
	}
	public void neuerCode(){
		//hier wartet sp�ter das JMS
		test.notify();
	}
	public String getNeuerCode(){
		return "TEST";
	}
	
}
