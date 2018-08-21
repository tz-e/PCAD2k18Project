package commons;

import java.io.Serializable;

public class Topic implements TopicInterface, Serializable {
	/**
	 * Classe concreta dell'interfaccia TopicInterface
	 * 
	 * @author Daniele Atzeni
	 */
	private static final long serialVersionUID = 1L;
	private String _name;
	private String _descr;

	public Topic(String name, String descr) {
		Utils.checkIfNull(name, descr);
		_name = name;
		_descr = descr;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		Utils.checkIfNull(name);
		_name = name;
	}

	@Override
	public String getDescription() {
		return _descr;
	}

	@Override
	public void setDescription(String descr) {
		Utils.checkIfNull(descr);
		_descr = descr;
	}

	@Override
	public int hashCode() {
		return _name.hashCode() * _descr.hashCode() * 11;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Topic))
			return false;
		if (obj == this)
			return true;

		Topic topic = (Topic) obj;
		return topic._descr.equals(_descr) && topic._name.equals(_name);
	}

	@Override
	public String toString() {
		return "Nome: " + _name + " Descrizione: " + _descr + System.lineSeparator();
	}
}
