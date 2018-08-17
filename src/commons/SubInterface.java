package commons;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;

public interface SubInterface  {
	void notifyClient(NewsInterface news) throws RemoteException, Exception;

}
