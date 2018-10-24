package mains.menu;

import java.io.Console;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import broker.PCADBroker;
import broker.PCADBrokerInterface;
import client.Client;
import client.ClientInterface;
import commons.News;
import commons.Topic;
import commons.TopicInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SameBrokerException;
import exceptions.SubscriberAlreadySubbedException;

import static commons.Utils.printMenuClient;
import static commons.Utils.getTheTopic;
import static commons.Utils.ipDell;
import static commons.Utils.LOCALHOST;
import static commons.Utils.SERVER_REMOTE;
import static commons.Utils.port;
import static commons.Utils.getAStringNotNull;
import static commons.Utils.showTheTopics;

public class serverMenu extends Thread {
	Console console = System.console();
	String nomeBroker;
	private PCADBrokerInterface serverToSubTo;

	String nomeBrokerToConnect = null;
	Topic tempTopic;
	int tempInt;
	PCADBrokerInterface server;
	PCADBrokerInterface stubRequest;
	List<TopicInterface> topicList = new LinkedList<TopicInterface>();
	Registry r = null;
	@Override
	public void run() {

		nomeBroker = getAStringNotNull(console, "Qual e' il nome di questo broker?");
		try {
			System.out.println(InetAddress.getLocalHost());

			System.setProperty("java.security.policy", "file:./sec.policy");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", LOCALHOST);
			// System.setProperty("java.rmi.server.hostname", "localhost");
			// System.setProperty("java.rmi.server.hostname", "0.0.0.0");
			Registry r = null;
			try {
				r = LocateRegistry.createRegistry(port);
			} catch (RemoteException e) {
				try {
					r = LocateRegistry.getRegistry(port);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println("Registro trovato");
			server = (PCADBrokerInterface) new PCADBroker();
			stubRequest = (PCADBrokerInterface) UnicastRemoteObject.exportObject(server, 0);
			r.rebind(nomeBroker, stubRequest);
		} catch (UnknownHostException | RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Ora sono pronto a ricevere richieste!");

		while (true) {
			switch (console.readLine(printMenuClient())) {
			case "1":
				if (nomeBrokerToConnect != null) {
					System.out.println(
							"Sei gia' iscritto a un broker, prima di effettuare questa operazione devi disconnetterti!");
					break;
				}
				nomeBrokerToConnect = getAStringNotNull(console, "Qual e' il nome del broker?");
				try {
					serverToSubTo=(PCADBrokerInterface) r.lookup(nomeBrokerToConnect);
					serverToSubTo.Connect(stubRequest);
				} catch (RemoteException | AlreadyConnectedException | SameBrokerException | NotBoundException e) {
					System.out.println("E' andato qualcosa storto, sicuro il nome sia corretto?");
					//e.printStackTrace();
					break;
				}
				System.out.println("Connesso al broker " + nomeBrokerToConnect + "!");
				break;
			case "2":
				try {
					stubRequest.Disconnect(serverToSubTo);
				} catch (RemoteException | NonExistentSubException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Disconnesso da " + nomeBrokerToConnect + "!");
				nomeBrokerToConnect = null;
				break;
			case "3":
				if (nomeBrokerToConnect == null) {
					System.out.println("Non sei connesso a nessun broker!");
					break;
				}
				try {
					tempTopic = new Topic(getAStringNotNull(console, "Qual e' il nome del topic?"),
							getAStringNotNull(console, "Qual e' la sua descrizione?"));
					stubRequest.Subscribe(serverToSubTo, tempTopic);
				} catch (RemoteException | SubscriberAlreadySubbedException | NonExistentSubException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				if (!topicList.contains(tempTopic))
					topicList.add(tempTopic);
				System.out.println("Sottoscritto al topic:" + tempTopic.toString() + " !");
				break;
			case "4":
				if (nomeBrokerToConnect == null) {
					System.out.println("Non sei connesso a nessun broker!");
					break;
				}
				if (topicList.isEmpty()) {
					System.out.println("Non sei iscritto a nessun topic.");
					break;
				}
				tempInt = getTheTopic(topicList, console, showTheTopics(topicList));
				try {
					stubRequest.Unsubscribe(serverToSubTo,topicList.get(tempInt));
				} catch (RemoteException | NonExistentTopicException | NonExistentSubException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Ora non riceverai piu' news dal topic selezionato!");
				topicList.remove(tempInt);
				break;
			case "5":
				if (nomeBrokerToConnect != null)
					try {
						stubRequest.Disconnect(serverToSubTo);
					} catch (RemoteException | NonExistentSubException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return;

			}

		}
	}
}
