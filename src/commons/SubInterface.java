package commons;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;

public interface SubInterface extends  Remote,Serializable {
	void notifyClient(NewsInterface news) throws RemoteException, Exception;

}
