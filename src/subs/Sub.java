package subs;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.TopicInterface;

public class Sub implements SubInterface {

	@Override
	public boolean Connect(PCADBrokerInterface broker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Disconnect(PCADBrokerInterface broker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void StopNotification() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Publish(NewsInterface news) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Unsubscribe(TopicInterface topic) {
		// TODO Auto-generated method stub
		
	}
	
}
