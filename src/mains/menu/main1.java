package mains.menu;

import java.io.Console;

public class main1 {

	public static void main(String[] args) {

		Console console = System.console();
		String c;
		while (true) {
			c = console.readLine("In questa istanza vuoi accedere come Client(0) o come Broker(1)?");
			if (c == "0") {
				// lancia thread client
				System.out.println("Client then");
				break;
			} else if (c == "1") {
				System.out.println("Broker then");
				break;
			} else
				System.out.println("Lettura errata, inserire 0 o 1");
		}

	}

}
