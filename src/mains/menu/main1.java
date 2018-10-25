package mains.menu;

import java.io.Console;
import java.util.Scanner;
import mains.menu.*;
public class main1 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Console console = System.console();
		String c;
		while (true) {
			System.out.println("In questa istanza vuoi accedere come Client(0) o come Broker(1)?");
			c = s.nextLine();
			if (c.equals("0")) {
				System.out.println("Client then");
				new clientMenu().start();
				break;
			} else if (c.equals("1")) {
				System.out.println("Broker then");
				new serverMenu().start();
				break;
			} else
				System.out.println("Lettura errata, inserire 0 o 1");
		}
		
	}

}
