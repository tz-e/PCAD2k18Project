package broker;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import commons.NewsInterface;
import commons.TopicInterface;
import subs.SubInterface;

public interface PCADBrokerInterface  extends Remote,Serializable{
	public void request(String x,SubInterface stub) throws RemoteException;

	public boolean Connect(SubInterface sub);
	public boolean Disconnect(SubInterface sub);
	public boolean Unsubscribe(SubInterface sub, TopicInterface topic);
	public void PublishNews(NewsInterface news, TopicInterface topic);
	
}
