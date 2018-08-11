package commons;

public class Topic implements TopicInterface {
	private String _name;
	private String _descr;
	public Topic(String name, String descr) {
		_name=name;
		_descr=descr;
	}
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name=name;
	}

	@Override
	public String getDescription() {
		return _descr;
	}

	@Override
	public void setDescription(String descr) {
		_descr=descr;
	}

}
