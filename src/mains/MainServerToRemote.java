package mains;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import broker.PCADBroker;
import broker.PCADBrokerInterface;

public class MainServerToRemote {
	public static void main(String args[]) {
		PCADBrokerInterface server;
		PCADBrokerInterface stubRequest;

	try {
		System.out.println(InetAddress.getLocalHost());
		System.setProperty("java.security.policy", "file:./sec.policy");
		// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Server/");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "127.0.1.1");
		//System.setProperty("java.rmi.server.hostname", "localhost");
		//System.setProperty("java.rmi.server.hostname", "0.0.0.0");
		Registry r = null;
		try {
			r = LocateRegistry.createRegistry(8000);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(8000);
		}
		System.out.println("Registro trovato");
		server = (PCADBrokerInterface) new PCADBroker();
		stubRequest = (PCADBrokerInterface) UnicastRemoteObject.exportObject(server, 0);
		r.rebind("S_REMOTE", stubRequest);
	}catch (Exception e) {
		System.out.println(e);
	}	}}
	
