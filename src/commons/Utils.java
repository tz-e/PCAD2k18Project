package commons;
/**
 * Questa e' la classe che contiene tutti i metodi
 *  di controllo comuni a tutte le funzionalita'
 * @author Daniele Atzeni
 */
public class Utils {
	public static String NOT_CONECTED="You are not connected to the Server";
	public static void checkIfNull(Object... objs) {
		if(objs==null) throw new IllegalArgumentException();
		for(Object obj:objs) {
			if(obj==null) throw new IllegalArgumentException();
		}
	}
}
