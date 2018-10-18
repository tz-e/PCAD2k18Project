package mains.local;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import broker.PCADBroker;
import broker.PCADBrokerInterface;
import commons.Topic;
import commons.TopicInterface;

public class ServerSendingNews extends Thread {
	private static PCADBrokerInterface stubRequest;
	private static PCADBrokerInterface server;
	private static PCADBrokerInterface serverToSubTo;
	private String myIp;
	private String serverIp;
	private int port;
	private String myName;
	private TopicInterface topic;

	public ServerSendingNews(String myIp, String myName, int port, TopicInterface topic) {
		this.myIp = myIp;
		this.serverIp = serverIp;
		this.myName = myName;
		this.port = port;
		this.topic = topic;
	}

	@Override
	public void run() {
		try {
			System.setProperty("java.security.policy", "file:./sec.policy");
			// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Server/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", myIp);
			Registry r = null;
			try {
				r = LocateRegistry.createRegistry(port);
			} catch (RemoteException e) {
				r = LocateRegistry.getRegistry(port);
			}
			System.out.println("Registro trovato");
			server = (PCADBrokerInterface) new PCADBroker();
			stubRequest = (PCADBrokerInterface) UnicastRemoteObject.exportObject(server, 0);
			r.rebind(myName, stubRequest);// "SERVER_SENDING_NEWS"
			System.out.println("Tutto ok");

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
