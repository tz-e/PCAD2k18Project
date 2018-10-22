package broker;

import java.rmi.RemoteException;

import java.util.concurrent.ConcurrentHashMap;

import client.ClientInterface;
import commons.MySet;
import commons.NewsInterface;
import commons.SubInterface;
import commons.TopicInterface;
import commons.Utils;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.SameBrokerException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public class PCADBroker implements PCADBrokerInterface {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private ConcurrentHashMap<SubInterface, Boolean> subList;
	private ConcurrentHashMap<TopicInterface, MySet<SubInterface>> subscribers;

	public PCADBroker() {
		// subList = new ConcurrentHashMap<SubInterface, Boolean>();
		subscribers = new ConcurrentHashMap<TopicInterface, MySet<SubInterface>>();
	}

	/**
	 * SUBSCRIBE
	 * 
	 * @throws NonExistentSubException
	 * @throws SubscriberAlreadySubbedException
	 **/
	@Override
	public void Subscribe(PCADBrokerInterface broker, TopicInterface topic)
			throws SubscriberAlreadySubbedException, NonExistentSubException {
		Utils.checkIfNull(broker, topic);
		actualSubscribe(broker, topic);
	}

	@Override
	public void Subscribe(ClientInterface sub, TopicInterface topic)
			throws SubscriberAlreadySubbedException, NonExistentSubException {
		Utils.checkIfNull(sub, topic);
		actualSubscribe(sub, topic);
	}

	private void actualSubscribe(SubInterface sub, TopicInterface topic)
			throws SubscriberAlreadySubbedException, NonExistentSubException {
		System.out.println((sub instanceof ClientInterface ? "Client " : "Broker ") + "subbed to " + topic.toString());
		/**
		 * Se il sub non e' connesso esco direttamente if (subList.computeIfPresent(sub,
		 * (key, value) -> value = new Boolean(false)) == null) throw new
		 * NonExistentSubException();
		 **/

		// if(subList.computeIfAbsent(sub, k-> null)==null) throw new
		// NonExistentSubException();

		if (subscribers.compute(topic,
				(k, v) -> v == null ? new MySet<SubInterface>(sub) : v.addAndReturn(sub)) == null)
			throw new SubscriberAlreadySubbedException();
		System.out.println(topic.toString()+ " ha: "+ subscribers.get(topic).size()+" subs");

		/**
		 * Se non esiste il topic allora lo creo
		 * 
		 * if (!subscribers.containsKey(topic)) { subscribers.put(topic, new
		 * LinkedList<SubInterface>(Arrays.asList(sub))); return; } /** Il topic esiste
		 * e il sub e' gia' iscritto, lancio l'eccezione
		 * 
		 * if (subscribers.get(topic).contains(sub)) throw new
		 * SubscriberAlreadySubbedException(); /** Il topic esiste e il sub non e'
		 * ancora iscritto, allora lo aggiungo
		 **/
		// subscribers.get(topic).add(sub);
	}
	/**
	 * STOP NOTIFICATION
	 * 
	 * Un client o un broker possono decidere di smettere di ricevere notifiche
	 * 
	 * @throws NonExistentSubException
	 **/
	/*
	 * @Override public void StopNotification(PCADBrokerInterface broker) throws
	 * NonExistentSubException { Utils.checkIfNull(broker);
	 * actualStopNotification(broker); }
	 * 
	 * @Override public void StopNotification(ClientInterface client) throws
	 * NonExistentSubException { Utils.checkIfNull(client);
	 * actualStopNotification(client); }
	 * 
	 * private void actualStopNotification(SubInterface sub) throws
	 * NonExistentSubException { /** Se il subscriber esiste a prescindere metto il
	 * suo valore a false, se non esiste l'ancio un'eccezione
	 **/

	// if (subList.computeIfPresent(sub, (key, value) -> value = new Boolean(false))
	// == null)
	// throw new NonExistentSubException();
	/*
	 * if (subList.containsKey(sub)) subList.put(sub, false); else throw new
	 * NonExistentSubException();
	 */

	// }

	/**
	 * UNSUBSCRIBE
	 * 
	 * Un Broker o un Client puo' decidere di togliere la propria iscrizione da un
	 * topic
	 * 
	 * @throws NonExistentSubException
	 * @throws NonExistentTopicException
	 **/
	@Override
	public void Unsubscribe(ClientInterface sub, TopicInterface topic)
			throws NonExistentTopicException, NonExistentSubException {
		Utils.checkIfNull(sub, topic);
		actualUnsubscribe(sub, topic);
	}

	@Override
	public void Unsubscribe(PCADBrokerInterface broker, TopicInterface topic)
			throws NonExistentTopicException, NonExistentSubException {
		Utils.checkIfNull(broker, topic);
		actualUnsubscribe(broker, topic);
	}

	private void actualUnsubscribe(SubInterface sub, TopicInterface topic)
			throws NonExistentTopicException, NonExistentSubException {
		// NonExistentTopicException va tolta o no?

		if (subscribers.computeIfPresent(topic, (k, v) -> v.removeAndReturn(sub)) == null)
			throw new NonExistentSubException();

		/**
		 * Se il topic non esiste esco direttamente
		 * 
		 * if (!subscribers.containsKey(topic)) throw new NonExistentTopicException();
		 * /** Se il sub non e' presente nella lista relativa al topic esco subito
		 * 
		 * if (!subscribers.get(topic).remove(sub)) throw new NonExistentSubException();
		 * /** Se cancello l'unico sub della lista cancello anche la key nella hastable
		 * 
		 * if (subscribers.get(topic).isEmpty()) { subscribers.remove(topic); }
		 **/
	}

	/**
	 * PUBLISH
	 * 
	 * Un Client o un Broker possono pubblicare una news da condividere con gli
	 * altri utenti
	 * @throws Exception
	 * @throws RemoteException
	 **/
	@Override
	public void PublishNews(NewsInterface news, TopicInterface topic)
			throws RemoteException {
		Utils.checkIfNull(news, topic);
		/**
		 * Se non esiste il topic lo creo
		 **/
		subscribers.computeIfAbsent(topic, v->new MySet<SubInterface>());
		System.out.println("ehy");
		/**
		 * Altrimenti ciclo per la lista dei subscribers iscritti a un certo topic
		 * controllando per ogni sub che non abbia deciso di silenziare le notifiche
		 **/
		Thread thr =new Thread(new PublishOperation(subscribers.get(topic).values(), news));
		thr.start();
		/*
		 * for (SubInterface sub : subscribers.get(topic)) { Boolean temp =
		 * subList.get(sub); if (temp != null && temp.booleanValue()) {
		 * System.out.println("Sending news to a " + sub.toString()); if (sub instanceof
		 * ClientInterface) ((ClientInterface) sub).notifyClient(news); else
		 * ((PCADBrokerInterface) sub).notifyBroker(news); } }
		 */
	}

	/**
	 * CONNECT
	 * 
	 * Un Client o un Broker per accedere al servizio devono in primo luogo
	 * connettersi, cosi' facendo vado a salvarmi il suo oggetto con cui
	 * successivamente andro' a comunicare
	 * 
	 * @throws SubscriberAlreadyConnectedException
	 **/
	@Override
	public void Connect(ClientInterface sub) throws RemoteException, SubscriberAlreadyConnectedException {
		Utils.checkIfNull(sub);
		ActualConnect(sub);
	}

	@Override
	public void Connect(PCADBrokerInterface broker)
			throws RemoteException, SubscriberAlreadyConnectedException, SameBrokerException {
		Utils.checkIfNull(broker);
		/**
		 * Non posso andare a connettere un broker con se stesso
		 **/
		if (this == broker)
			throw new SameBrokerException();
		ActualConnect(broker);
	}

	private void ActualConnect(SubInterface sub) throws SubscriberAlreadyConnectedException {
		/**
		 * Controllo che l'utente non si sia gia' connesso, in quel caso ritorno false
		 * 
		 * if (subList.containsKey(sub)) throw new
		 * SubscriberAlreadyConnectedException(); /** In caso contrario lo aggiungo alla
		 * lista di sub salvandomi il suo oggetto
		 * 
		 * subList.put(sub, true);
		 */
		System.out.println("Connection requested by " + (sub instanceof ClientInterface ? "Client " : "Broker"));
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
		System.out.println("Connection accepted by " + (sub instanceof ClientInterface ? "Client " : "Broker"));

	}

	/**
	 * DISCONNECT
	 * 
	 * Un Client o un Broker per togliersi completamente dal servizio devono
	 * effettuare un'operazione di disconnect
	 * @throws NonExistentSubException
	 **/
	@Override
	public void Disconnect(PCADBrokerInterface broker) throws NonExistentSubException {
		Utils.checkIfNull(broker);
		actualDisconnect(broker);
	}

	@Override
	public void Disconnect(ClientInterface sub) throws NonExistentSubException {
		Utils.checkIfNull(sub);
		actualDisconnect(sub);
	}

	private void actualDisconnect(SubInterface sub) throws NonExistentSubException {
		/**
		 * Se il sub non e' presente ritorno false, in caso contrario lo elimino
		 * 
		 * if (!subList.containsKey(sub)) throw new NonExistentSubException();
		 * subList.remove(sub); 
		 /** 
		  * Elimino l'utente da tutte le liste a cui era iscritto
		 **/
		
		for (MySet<SubInterface> l : subscribers.values())
			l.remove(sub);
	}

	private void deleteFromLists(SubInterface sub) {
		for (MySet<SubInterface> l : subscribers.values())
			l.remove(sub);
	}

	@Override
	public void notifyBroker(NewsInterface news) throws RemoteException, NonExistentTopicException {

		if (news == null)
			System.out.println("Handshake ok!");
		else {
			System.out.println("Notify request received - Broker");
			PublishNews(news, news.GetTopic());
		}
	}

	@Override
	public int hashCode() {
		return subscribers.hashCode() * 11;
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
