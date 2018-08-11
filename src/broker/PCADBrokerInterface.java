package broker;

import commons.NewsInterface;
import commons.TopicInterface;
import subs.SubInterface;

public interface PCADBrokerInterface {
	public boolean Connect(SubInterface sub);
	public boolean Disconnect(SubInterface sub);
	public boolean Unsubscribe(SubInterface sub, TopicInterface topic);
	public void PublishNews(NewsInterface news, TopicInterface topic);
	
}
