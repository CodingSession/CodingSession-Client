package businesslogik;

import java.util.HashMap;
import javax.jms.JMSException;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class KommunikationOutgoing extends Kommunikation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MessageProducer producerCode;
	TextMessage textMessage;

	public KommunikationOutgoing(Object lockCode, Object lockEinladung) {
		super(lockCode, lockEinladung);
		try {
			topicEinladung = session.createTopic("Einladung");
			producerCode = session.createProducer(topicEinladung);
			producerCode.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	public void starteCsKommu(String topic){
		try {
			topicCode = session.createTopic(topic);
			producerCode = session.createProducer(topicCode);
			producerCode.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void ver�ffentlicheCode(String nachricht, String sender) {
		// code an topic geschrieben
		try {
			textMessage = session.createTextMessage(nachricht);
			producerCode.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public void ladeEin(HashMap<String, String> cs, int freund) {
		try {
			ObjectMessage om = session.createObjectMessage(cs);
			om.setIntProperty("id", freund);
			System.out.println("om erstellt");
			producerCode.send(om);
			System.out.println("Ver�ffentlicht");
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
}
