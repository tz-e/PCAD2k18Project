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

public class PCADBroker implements PCADBrokerInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcurrentList<TopicInterface> topicList;
	private ConcurrentHashMap<SubInterface, Boolean> subList;
	private ConcurrentHashMap<TopicInterface, List<SubInterface>> subscribers;
	public PCADBroker() {
		topicList=new ConcurrentList<TopicInterface>();
		subList=new ConcurrentHashMap<SubInterface, Boolean>();
		subscribers=new ConcurrentHashMap<TopicInterface, List<SubInterface>>(); // temporaneamente la lista e' di object, successivamente creero' un'interfaccia 
	}
	/**
	 * SUBSCRIBE
	 * **/
	@Override
	public boolean Subscribe(PCADBrokerInterface broker, Topic topic) {
		return actualSubscribe(broker, topic);
	}
	@Override
	public boolean Subscribe(ClientInterface sub, TopicInterface topic) {
		return actualSubscribe(sub, topic);
	}

	private boolean actualSubscribe(SubInterface sub, TopicInterface topic) {
		if(!topicList.contains(topic)) { 
			/**
			 * Se non esiste il topic allora non esiste neanche nella hashMap
			 * **/
			topicList.add(topic);
			subscribers.put(topic,new LinkedList<SubInterface>(Arrays.asList(sub)));
			return true;
		}
		/**
		 * Il topic esiste e il sub e' gia' iscritto, ritorno false**/
		if(subscribers.get(topic).contains(sub)) return false; 
		subscribers.get(topic).add(sub);
		return true;
	}

	/**
	 * STOP NOTIFICATION
	 * **/
	@Override
	public boolean StopNotification(PCADBrokerInterface broker) {
		return actualStopNotification(broker);	
	}

	@Override
	public boolean StopNotification(ClientInterface client) {
		return actualStopNotification(client);
	}

	private boolean actualStopNotification(SubInterface client) {
		if(subList.containsKey(client))	subList.put(client, false); 
		else return false;
		return true;
	}
	
	/**
	 * UNSUBSCRIBE
	 * **/
	@Override
	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic) {
		return actualUnsubscribe(sub, topic);
	}
	
	@Override
	public boolean Unsubscribe(PCADBrokerInterface broker, TopicInterface topic) {
		return actualUnsubscribe(broker, topic);
	}
	
	private boolean actualUnsubscribe(Object sub, TopicInterface topic) {
		if(!subscribers.get(topic).contains(sub)) return false;
		subscribers.get(topic).remove(sub);
		return true;
	}
	
	@Override
	public void PublishNews(NewsInterface news, TopicInterface topic) throws Exception {
		if(!subscribers.containsKey(topic)) throw new Exception();
		for(SubInterface sub: subscribers.get(topic)) {
			sub.notifyClient(news);
		}
		
	}

	@Override
	public boolean Connect(ClientInterface sub) {
		if(subList.containsKey(sub)) return false;
		subList.put(sub, true); 
		return true;
	}

	@Override
	public boolean Disconnect(ClientInterface sub) {
		if(!subList.containsKey(sub)) return false;
		subList.remove(sub);
		return deleteFromLists(sub);
	}

	private boolean deleteFromLists(ClientInterface sub) {
		for(List<SubInterface> list: subscribers.values()){
			list.remove(sub);
		}
		return true;
	}
	@Override
	public void notifyClient(NewsInterface news) throws RemoteException {
		// TODO Auto-generated method stub
		
	}



	

	

	

	

}
