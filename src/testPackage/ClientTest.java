package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;

import broker.PCADBroker;
import client.Client;
import client.ClientInterface;

public class ClientTest {
	private final ClientInterface client=new Client(new PCADBroker());
	@Test
	public void ConnectReturnsOk() {
		try {
			assertTrue(client.Connect());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void ConnectTwiceReturnsFalse() {
		try {
			client.Connect();
			assertFalse(client.Connect());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
