package mains.remote;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import broker.PCADBroker;
import broker.PCADBrokerInterface;

import static commons.Utils.SERVER_REMOTE;
import static commons.Utils.ipDell;
import static commons.Utils.LOCALHOST;

import static commons.Utils.port;


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
		System.setProperty("java.rmi.server.hostname", LOCALHOST);
		//System.setProperty("java.rmi.server.hostname", "localhost");
		//System.setProperty("java.rmi.server.hostname", "0.0.0.0");
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
		r.rebind(SERVER_REMOTE, stubRequest);
		}
		catch (UnknownHostException | RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
