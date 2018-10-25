package mains.menu;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import client.Client;
import client.ClientInterface;
import commons.News;
import commons.Topic;
import commons.TopicInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadySubbedException;

import static commons.Utils.printMenuClient;
import static commons.Utils.getTheTopic;
import static commons.Utils.LOCALHOST;
import static commons.Utils.port;
import static commons.Utils.getAStringNotNull;
import static commons.Utils.showTheTopics;
import static commons.Utils.getLineWithMessage;

public class clientMenu extends Thread {
	Scanner scanner = new Scanner(System.in);
	ClientInterface client;
	String nameClient;
	String nomeBroker = null;
	Topic tempTopic;
	int tempInt;
	List<TopicInterface> topicList = new LinkedList<TopicInterface>();

	@Override
	public void run() {

		nameClient = getAStringNotNull(scanner, "Qual e' il nome di questo client?");
		String choice;

		while (true) {
			System.out.println(printMenuClient());

			switch (scanner.nextLine()) {
			case "1":
				if (nomeBroker != null) {
					System.out.println(
							"Sei gia' iscritto a un broker, prima di effettuare questa operazione devi disconnetterti!");
					break;
				}
				nomeBroker = getAStringNotNull(scanner, "Qual e' il nome del broker?");
				client = new Client(LOCALHOST, LOCALHOST, nomeBroker, port);
				try {
					client.Connect();
				} catch (RemoteException | AlreadyConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Connesso al broker " + nomeBroker + "!");
				break;
			case "2":
				if (nomeBroker == null) {
					System.out.println("Non sei connesso a nessun broker!");
					break;
				}
				try {
					client.Disconnect();
				} catch (RemoteException | NonExistentSubException | NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Disconnesso da " + nomeBroker + "!");
				nomeBroker = null;
				break;
			case "3":
				if (nomeBroker == null) {
					System.out.println("Non sei connesso a nessun broker!");
					break;
				}
				try {
					tempTopic = new Topic(getAStringNotNull(scanner, "Qual e' il nome del topic?"),
							getAStringNotNull(scanner, "Qual e' la sua descrizione?"));
					client.Subscribe(tempTopic);

				} catch (RemoteException | SubscriberAlreadySubbedException | NonExistentSubException
						| NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				if (!topicList.contains(tempTopic))
					topicList.add(tempTopic);
				System.out.println("Sottoscritto al topic:" + tempTopic.toString() + " !");
				break;
			case "4":
				if (nomeBroker == null) {
					System.out.println("Non sei connesso a nessun broker!");
					break;
				}
				if (topicList.isEmpty()) {
					System.out.println("Non sei iscritto a nessun topic.");
					break;
				}
				tempInt = getTheTopic(topicList, scanner, showTheTopics(topicList));
				try {
					client.Unsubscribe(topicList.get(tempInt));
				} catch (RemoteException | NonExistentTopicException | NonExistentSubException
						| NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Ora non riceverai piu' news dal topic selezionato!");
				topicList.remove(tempInt);
				break;
			case "5":
				if (nomeBroker == null) {
					System.out.println("Non sei connesso a nessun broker!");
					break;
				}

				tempInt = Integer.parseInt(getLineWithMessage(scanner,
						"La news che vuoi pubblicare e' relativa a uno dei seguenti topic? Scegli la sua posizione nel caso, -1 altrimenti.\n"
								+ showTheTopics(topicList)));
				try {
					if (tempInt == -1) {
						client.Publish(new News(
								new Topic(getLineWithMessage(scanner, "Qual e' il titolo del topic?"),
										getLineWithMessage(scanner, "Qual e' la descrizione del topic?")),
								getLineWithMessage(scanner, "Qual e' il messaggio della news?")));

					} else if ((tempInt >= 0 && tempInt < topicList.size()))
						client.Publish(new News(topicList.get(tempInt),
								getLineWithMessage(scanner, "Qual e' il messaggio della news?")));
					else
						System.out.println("Hai inserito un valore non corretto");
				} catch (RemoteException | NonExistentTopicException | NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "6":
				if (nomeBroker != null)
					try {
						client.ReadNews();
					} catch (RemoteException | NotConnectedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				break;
			case "7":
				if (nomeBroker != null)
					try {
						client.stopReadNews();
					} catch (RemoteException | NotConnectedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				break;
			case "8":
				if (nomeBroker != null)
					try {
						client.Disconnect();
					} catch (RemoteException | NonExistentSubException | NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				scanner.close();
				return;
			default:
				System.out.println("Inserisci un valore valido");
			}

		}
		
	}
	
}
