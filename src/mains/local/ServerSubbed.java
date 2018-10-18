package mains.local;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import broker.PCADBroker;
import broker.PCADBrokerInterface;
import commons.News;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;

public class ServerSubbed extends Thread {
	private PCADBrokerInterface stubRequest;
	private PCADBrokerInterface server;
	private PCADBrokerInterface serverToSubTo;
	private String myIp;
	private String serverIp;
	private String serverName;
	private int port;
	private String myName;
	private TopicInterface topic;
	
	public ServerSubbed(String myIp, String serverIp, String serverName,
			 String myName, int port, TopicInterface topic)
	{
		this.myIp=myIp;
		this.serverIp=serverIp;
		this.serverName=serverName;
		this.myName=myName;
		this.port=port;
		this.topic=topic;
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
			r.rebind(myName, stubRequest);//"SERVER_SUBBED"

			
			serverToSubTo = (PCADBrokerInterface) r.lookup(serverName);//"SERVER_SENDING_NEWS"
			serverToSubTo.Connect(stubRequest);
			serverToSubTo.Subscribe(stubRequest, topic);

			System.out.println("Tutto ok");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
