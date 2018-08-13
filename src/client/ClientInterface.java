package client;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.TopicInterface;

public interface ClientInterface extends  Remote,Serializable {
	
	public void notifyClient() throws RemoteException;
	public boolean Connect();
	public boolean Disconnect();
	
	
	
}	
