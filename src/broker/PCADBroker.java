package broker;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import client.ClientInterface;
import commons.ConcurrentList;
import commons.NewsInterface;
import commons.SubBrokerInterface;
import commons.SubInterface;
import commons.Topic;
import commons.TopicInterface;

public class PCADBroker implements PCADBrokerInterface, SubBrokerInterface{

	private ConcurrentList<TopicInterface> topicList;
	private ConcurrentHashMap<TopicInterface, List<ClientInterface>> subs;
	@Override
	public void Subscribe(PCADBrokerInterface broker, Topic topic) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean Subscribe(ClientInterface sub, TopicInterface topic) {
		if(!topicList.contains(topic)) { 
			/**
			 * Se non esiste il topic allora non esiste neanche nella hashMap**/
			topicList.add(topic);
			subs.put(topic,new LinkedList<ClientInterface>(Arrays.asList(sub)));
			return true;
		}
		/**
		 * Il topic esiste e il sub e' gia' iscritto, ritorno false**/
		if(subs.get(topic).contains(sub)) return false; 
		subs.get(topic).add(sub);
		return true;
		
	}

	@Override
	public void StopNotification(PCADBrokerInterface broker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopNotification(ClientInterface client) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean Unsubscribe(PCADBrokerInterface broker, TopicInterface topic) {
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



	

	

	

	

}
