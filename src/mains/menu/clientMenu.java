package mains.menu;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import client.Client;
import client.ClientInterface;
import commons.Topic;
import commons.TopicInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadySubbedException;

import static commons.Utils.printMenuClient;
import static commons.Utils.LOCALHOST;
import static commons.Utils.port;
import static commons.Utils.getAStringNotNull;

public class clientMenu extends Thread {
	Console console = System.console();
	ClientInterface client;
	String nameClient;
	String nomeBroker = null;
	Topic tempTopic;
	List<TopicInterface> topicList = new LinkedList<TopicInterface>();
	@Override
	public void run() {

		nameClient = getAStringNotNull(console, "Qual e' il nome di questo client?");
		String choice;
		while (true) {
			switch (console.readLine(printMenuClient())) {
			case "1":
				nomeBroker = getAStringNotNull(console, "Qual e' il nome del broker?");
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
				
				try {
					tempTopic=new Topic(getAStringNotNull(console, "Qual e' il nome del topic?"),
							getAStringNotNull(console, "Qual e' la sua descrizione?"));
					client.Subscribe(tempTopic);
					
				} catch (RemoteException | SubscriberAlreadySubbedException | NonExistentSubException
						| NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				if(!topicList.contains(tempTopic))
					topicList.add(tempTopic);
				System.out.println("Sottoscritto al topic:"+tempTopic.toString()+ " !");
				break;
			case "4":
				
				
				


			}
		}
	}
}
