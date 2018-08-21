package broker;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import client.ClientInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.TopicInterface;
import commons.Utils;

public class PCADBroker implements PCADBrokerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private ConcurrentList<TopicInterface> topicList;
	private ConcurrentHashMap<SubInterface, Boolean> subList;
	private ConcurrentHashMap<TopicInterface, List<SubInterface>> subscribers;

	public PCADBroker() {
		// topicList = new ConcurrentList<TopicInterface>();
		subList = new ConcurrentHashMap<SubInterface, Boolean>();
		subscribers = new ConcurrentHashMap<TopicInterface, List<SubInterface>>();
	}

	/**
	 * SUBSCRIBE
	 **/
	@Override
	public boolean Subscribe(PCADBrokerInterface broker, TopicInterface topic) {
		Utils.checkIfNull(broker, topic);
		return actualSubscribe(broker, topic);
	}

	@Override
	public boolean Subscribe(ClientInterface sub, TopicInterface topic) {
		Utils.checkIfNull(sub, topic);
		return actualSubscribe(sub, topic);
	}

	private boolean actualSubscribe(SubInterface sub, TopicInterface topic) {
		System.out.println((sub instanceof ClientInterface ? "Client " : "Broker ") + "subbed to " + topic.toString());
		if (!subscribers.containsKey(topic)) {
			/**
			 * Se non esiste il topic allora lo creo
			 **/
			// topicList.add(topic);
			subscribers.put(topic, new LinkedList<SubInterface>(Arrays.asList(sub)));
			return true;
		}
		/**
		 * Il topic esiste e il sub e' gia' iscritto, ritorno false
		 **/
		if (subscribers.get(topic).contains(sub))
			return false;
		/**
		 * Il topic esiste e il sub non e' ancora iscritto, allora lo aggiungo e ritorno
		 * true
		 **/
		subscribers.get(topic).add(sub);
		return true;
	}

	/**
	 * STOP NOTIFICATION
	 * 
	 * Un client o un broker possono decidere di smettere di ricevere notifiche
	 **/
	@Override
	public boolean StopNotification(PCADBrokerInterface broker) {
		Utils.checkIfNull(broker);
		return actualStopNotification(broker);
	}

	@Override
	public boolean StopNotification(ClientInterface client) {
		Utils.checkIfNull(client);
		return actualStopNotification(client);
	}

	private boolean actualStopNotification(SubInterface sub) {
		/**
		 * Se il subscriber esiste a prescindere metto il suo valore a false, se non
		 * esiste ritorno false
		 **/
		if (subList.containsKey(sub))
			subList.put(sub, false);
		else
			return false;
		return true;
	}

	/**
	 * UNSUBSCRIBE
	 * 
	 * Un Broker o un Client puo' decidere di togliere la propria iscrizione da un
	 * topic
	 **/
	@Override
	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic) {
		Utils.checkIfNull(sub, topic);
		return actualUnsubscribe(sub, topic);
	}

	@Override
	public boolean Unsubscribe(PCADBrokerInterface broker, TopicInterface topic) {
		Utils.checkIfNull(broker, topic);
		return actualUnsubscribe(broker, topic);
	}

	private boolean actualUnsubscribe(SubInterface sub, TopicInterface topic) {
		/**
		 * Se il sub non e' presente nella lista relativa al topic esco subito
		 **/
		if (!subscribers.get(topic).contains(sub))
			return false;
		subscribers.get(topic).remove(sub);
		/**
		 * Se cancello l'unico sub della lista cancello anche la key nella hastable
		 **/
		if (subscribers.get(topic).isEmpty()) { // sub
			subscribers.remove(topic);
		}
		return true;
	}

	/**
	 * PUBLISH
	 * 
	 * Un Client o un Broker possono pubblicare una news da condividere con gli
	 * altri utenti
	 **/
	@Override
	public void PublishNews(NewsInterface news, TopicInterface topic) throws Exception {
		Utils.checkIfNull(news, topic);
		/**
		 * Se non esiste il topic esco direttamente
		 **/
		if (!subscribers.containsKey(topic))
			return;
		/**
		 * Altrimenti ciclo per la lista dei subscribers iscritti a un certo topic
		 * controllando per ogni sub che non abbia deciso di silenziare le notifiche
		 **/
		System.out.println("Number of subs: " + subscribers.get(topic).size());
		for (SubInterface sub : subscribers.get(topic))
			if (subList.get(sub)) {
				System.out.println("Sending news to a " + sub.toString());
				if (sub instanceof ClientInterface)
					((ClientInterface) sub).notifyClient(news);
				else
					((PCADBrokerInterface) sub).notifyBroker(news);
			}
	}

	/**
	 * CONNECT 
	 * 
	 * Un Client o un Broker per accedere al servizio devono in primo luogo
	 * connettersi, cosi' facendo vado a salvarmi il suo oggetto con cui successivamente
	 * andro' a comunicare
	 **/
	@Override
	public boolean Connect(ClientInterface sub) throws RemoteException {
		Utils.checkIfNull(sub);
		return ActualConnect(sub);
	}

	@Override
	public boolean Connect(PCADBrokerInterface broker) throws RemoteException {
		Utils.checkIfNull(broker);
		return ActualConnect(broker);
	}

	private boolean ActualConnect(SubInterface sub) {
		/**
		 * Controllo che l'utente non si sia gia' connesso, in quel caso ritorno false
		 **/
		if (subList.containsKey(sub))
			return false;
		/**
		 * In caso contrario lo aggiungo alla lista di sub salvandomi il suo oggetto
		 **/
		subList.put(sub, true);
		System.out.println("Connection accepted by " + (sub instanceof ClientInterface ? "Client " : "Broker"));
		try {
			/**
			 * Dopo aver dichiarato di aver accettato la connessione notifico la notizia a
			 * chi ha richiesto il servizio
			 **/
			if (sub instanceof ClientInterface)
				((ClientInterface) sub).notifyClient(null);
			else
				((PCADBrokerInterface) sub).notifyBroker(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * DISCONNECT 
	 * 
	 * Un Client o un Broker per togliersi completamente dal servizio
	 * devono effettuare un'operazione di disconnect
	 **/
	@Override
	public boolean Disconnect(PCADBrokerInterface broker) {
		Utils.checkIfNull(broker);
		return actualDisconnect(broker);

	}

	@Override
	public boolean Disconnect(ClientInterface sub) {
		Utils.checkIfNull(sub);
		return actualDisconnect(sub);
	}

	private boolean actualDisconnect(SubInterface sub) {
		/**
		 * Se il sub non e' presente ritorno false, in caso contrario lo elimino
		 **/
		if (!subList.containsKey(sub))
			return false;
		subList.remove(sub);
		/**
		 * Successivamente elimino l'utente da tutte le liste a cui era iscritto
		 **/
		return deleteFromLists(sub);
	}

	private boolean deleteFromLists(SubInterface sub) {
		for (List<SubInterface> l : subscribers.values()) {
			l.remove(sub);
		}
		return true;
	}

	@Override
	public void notifyBroker(NewsInterface news) throws Exception {
		Utils.checkIfNull(news);
		if (news == null)
			System.out.println("Handshake ok!");
		else {
			System.out.println("Notify request received - Broker");
			PublishNews(news, news.GetTopic());
		}
	}

	@Override
	public int hashCode() {
		return subList.hashCode() * subscribers.hashCode() * 11;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PCADBroker))
			return false;
		if (obj == this)
			return true;

		PCADBroker br = (PCADBroker) obj;
		return br.subscribers.equals(subscribers);
	}


}
