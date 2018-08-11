package broker;

import commons.NewsInterface;
import commons.TopicInterface;
import subs.SubInterface;

public class PCADBroker implements PCADBrokerInterface{

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

}
