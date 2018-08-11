package commons;

public class News implements NewsInterface {
	private TopicInterface _topic;
	private String _text;
	public News(TopicInterface topic, String text) {
		_topic=topic;
		_text=text;
		
	}
	public TopicInterface get_topic() {
		return _topic;
	}
	public void set_topic(TopicInterface _topic) {
		this._topic = _topic;
	}
	public String get_text() {
		return _text;
	}

	public void set_text(String _text) {
		this._text = _text;
	}
	
}
