package subs;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.TopicInterface;

public interface SubInterface {
	public boolean Connect(PCADBrokerInterface broker);
	public boolean Disconnect(PCADBrokerInterface broker);
	public void StopNotification();
	public void Publish(NewsInterface news);
	public void Unsubscribe(TopicInterface topic);
	
	
}	
