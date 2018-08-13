package commons;

public class News implements NewsInterface {
	private TopicInterface _topic;
	private String _text;
	public News(TopicInterface topic, String text) {
		_topic=topic;
		_text=text;
		
	}

	public void SetTopic(TopicInterface _topic) {
		this._topic = _topic;
	}
	@Override
	public TopicInterface GetTopic() {
		return _topic;
	}
	public void SetText(String _text) {
		this._text = _text;
	}

	@Override
	public String GetText() {
		return _text;

	}
	
}
