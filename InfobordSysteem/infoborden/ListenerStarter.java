package infoborden;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public  class ListenerStarter implements Runnable, ExceptionListener {
	private String selector="";
	private Infobord infobord;
	private Berichten berichten;
	
	public ListenerStarter() {
	}
	
	public ListenerStarter(String selector, Infobord infobord, Berichten berichten) {
		this.selector=selector;
		this.infobord=infobord;
		this.berichten=berichten;
	}

	public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = 
            		new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
//			TODO maak de connection aan
//          Connection connection = ?????;
//          connection.start();
//          connection.setExceptionListener(this);
			Connection connection = connectionFactory.createConnection();
			connection.start();

//			TODO maak de session aan
//          Session session = ?????;
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//			TODO maak de destination aan
//          Destination destination = ?????;
			Destination destination = session.createTopic(selector);

//			TODO maak de consumer aan
//          MessageConsumer consumer = ?????;
            System.out.println("Produce, wait, consume"+ selector);
			MessageConsumer consumer = session.createConsumer(destination);
//			TODO maak de Listener aan
//          consumer.?????;
			consumer.receive();
			consumer.setMessageListener(new QueueListener(selector, infobord, berichten));

		} catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}