package commons;

/**
 * Questa interfaccia rappresenta una notizia mandata da un Broker a tutti i
 * subscribers, i quali possono essere broker o semplici client
 * 
 * @author Daniele Atzeni
 */
public interface NewsInterface {
	public TopicInterface GetTopic();

	public String GetText();
}
