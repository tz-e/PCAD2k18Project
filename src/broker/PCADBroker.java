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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcurrentList<TopicInterface> topicList;
	private ConcurrentHashMap<Object, Boolean> subList;
	private ConcurrentHashMap<TopicInterface, List<Object>> subscribers;
	public PCADBroker() {
		topicList=new ConcurrentList<TopicInterface>();
		subList=new ConcurrentHashMap<Object, Boolean>();
		subscribers=new ConcurrentHashMap<TopicInterface, List<Object>>(); // temporaneamente la lista e' di object, successivamente creero' un'interfaccia 
	}
	
	@Override
	public boolean Subscribe(PCADBrokerInterface broker, Topic topic) {
		return actualSubscribe(broker, topic);
		
	}
	@Override
	public boolean Subscribe(ClientInterface sub, TopicInterface topic) {
		return actualSubscribe(sub, topic);
	}

	private boolean actualSubscribe(Object sub, TopicInterface topic) {
		if(!topicList.contains(topic)) { 
			/**value
			 * Se non esiste il topic allora non esiste neanche nella hashMap**/
			topicList.add(topic);
			subscribers.put(topic,new LinkedList<Object>(Arrays.asList(sub)));
			return true;
		}
		/**
		 * Il topic esiste e il sub e' gia' iscritto, ritorno false**/
		if(subscribers.get(topic).contains(sub)) return false; 
		subscribers.get(topic).add(sub);
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
		if(subList.containsKey(sub)) return false;
		subList.put(sub, true); //aggiungere effettivamente il socket del client
		return true;
	}

	@Override
	public boolean Disconnect(ClientInterface sub) {
		// TODO Auto-generated method stub
		return false;
	}



	

	

	

	

}
