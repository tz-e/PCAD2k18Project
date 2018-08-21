package commons;

import java.io.Serializable;

public class News implements NewsInterface, Serializable {
	/**
	 * Implementazione effettiva di NewsInterface
	 * @author Daniele Atzeni
	 */
	private static final long serialVersionUID = 1L;
	private TopicInterface _topic;
	private String _text;
	public News(TopicInterface topic, String text) {
		Utils.checkIfNull(topic, text);
		_topic=topic;
		_text=text;
	}

	public void SetTopic(TopicInterface _topic) {
		Utils.checkIfNull(_topic);
		this._topic = _topic;
	}
	@Override
	public TopicInterface GetTopic() {
		return _topic;
	}
	public void SetText(String _text) {
		Utils.checkIfNull(_text);
		this._text = _text;
	}

	@Override
	public String GetText() {
		return _text;

	}

    @Override
    public int hashCode() {
        return 11*_topic.hashCode()*_text.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof News))
            return false;
        if (obj == this)
            return true;

        News news = (News) obj;
        return news._topic.equals(_topic) && news._text.equals(_text);
    }
    @Override
    public String toString() {
    	return _topic.toString()+"Testo: "+_text.toString();
    }
	
}
