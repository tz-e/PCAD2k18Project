package broker;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import client.ClientInterface;
import commons.NewsInterface;
import commons.SubBrokerInterface;
import commons.SubInterface;
import commons.Topic;
import commons.TopicInterface;

public class PCADBroker implements PCADBrokerInterface, SubBrokerInterface{

	private ConcurrentSkipListSet<TopicInterface> topicList;
	private ConcurrentHashMap<TopicInterface, List<ClientInterface>> subs;
	
	@Override
	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void PublishNews(NewsInterface news, TopicInterface topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Connect(ClientInterface sub) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Disconnect(ClientInterface sub) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Subscribe(PCADBrokerInterface broker, Topic topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopNotification(PCADBrokerInterface broker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Subscribe(ClientInterface sub, TopicInterface topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void StopNotification(ClientInterface client) {
		// TODO Auto-generated method stub
		
	}

	

	

	

}
