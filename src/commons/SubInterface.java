package commons;

import client.ClientInterface;

public interface SubInterface {
	public void Subscribe(Topic topic);
	public void StopNotification(ClientInterface sub);
	public void Publish(NewsInterface news);
	public void Unsubscribe(TopicInterface topic);
}
