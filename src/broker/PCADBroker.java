package broker;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import commons.NewsInterface;
import commons.TopicInterface;
import subs.SubInterface;

public class PCADBroker implements PCADBrokerInterface{

	private ConcurrentSkipListSet<TopicInterface> topicList;
	private ConcurrentHashMap<TopicInterface, List<SubInterface>> su;
	@Override
	public boolean Unsubscribe(SubInterface sub, TopicInterface topic) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void PublishNews(NewsInterface news, TopicInterface topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Connect(SubInterface sub) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Disconnect(SubInterface sub) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void request(String x, SubInterface stub) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
