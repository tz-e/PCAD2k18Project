package mains;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import broker.PCADBroker;
import broker.PCADBrokerInterface;
import client.ClientInterface;
import commons.News;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;

public class MainServer1 {
	/*private static PCADBrokerInterface server;
	private static PCADBrokerInterface serverToConnect;
	private static PCADBrokerInterface stubRequest;

	public static void main(String[] args) {

		try {
			System.setProperty("java.security.policy", "file:./sec.policy");
			// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", "localhost");
			// Registry r = LocateRegistry.getRegistry("localhost",8000);
			Registry r = LocateRegistry.getRegistry(8000);
			System.out.println("Registro trovato");
			server = (PCADBrokerInterface) new PCADBroker();
			stubRequest = (PCADBrokerInterface) UnicastRemoteObject.exportObject(server, 0);
			r.rebind("SERVER_1", stubRequest);

			serverToConnect = (PCADBrokerInterface) r.lookup("SERVER_2");
			// stub = (PCADBrokerInterface) UnicastRemoteObject.exportObject(server, 0);
			serverToConnect.Connect(stubRequest);

		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		TopicInterface topic = new Topic("Sport", "VIVA IL PALLONE");
		boolean exit = false;
		Scanner sc = new Scanner(System.in);

		while (!exit) {
			//ClientMain1.printMenu();
			try {
				// choice=System.in.read();
				switch (sc.nextInt()) {
				case 1:
					System.out.println("eheehe");
					stubRequest.Connect(serverToConnect);
					break;
				case 2:
					stubRequest.Disconnect(serverToConnect);
					break;
				case 3:
					stubRequest.StopNotification(serverToConnect);
					break;
				case 4:

					break;
				case 5:
					stubRequest.Unsubscribe(serverToConnect, topic);
					break;
				case 6:
					stubRequest.Subscribe(serverToConnect, topic);
					break;
				case 7:
					break;
				case 8:
					exit = true;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
*/
}
