package mains;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.sun.corba.se.spi.activation.Server;

import broker.PCADBroker;
import commons.SubInterface;

public class MainServer {
	public static void main(String args[]) {
		try {
			System.setProperty("java.security.policy","file:./sec.policy");
			System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Server/");
			if(System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname","localhost");
			Registry r = null;
			try {
				r = LocateRegistry.createRegistry(8000);
			} catch (RemoteException e) {
				r = LocateRegistry.getRegistry(8000);
			}
			System.out.println("Registro trovato");
			SubInterface server = (SubInterface) new PCADBroker();
			SubInterface stubRequest = (SubInterface) UnicastRemoteObject.exportObject(server,0);
			r.rebind("REG", stubRequest);
		    System.out.println("Tutto ok");
		    
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	 }
}
