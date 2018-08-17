package mains;

import java.util.Scanner;

import client.Client;
import commons.News;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;

public class ClientMain1 {

	public static void main(String [] args) {
		Client client = new Client();
		boolean exit = false;
		Scanner sc=new Scanner(System.in);
		int choice;
		System.out.println("ehhe");

		TopicInterface topic = new Topic("Sport", "VIVA IL PALLONE");
		while (!exit) {
			printMenu();
			try {
				//choice=System.in.read();
				switch (sc.nextInt()) {
				case 1:
					System.out.println("eheehe");
					client.Connect();
					break;
				case 2:
					client.Disconnect();
					break;
				case 3:
					client.StopNotification();
					break;
				case 4:

					NewsInterface news = new News(topic, "lUL");
					client.Publish(news);
					break;
				case 5:
					client.Unsubscribe(topic);
					break;
				case 6:
					client.Subscribe(topic);
					break;
				case 7:
					client.ReadNews();
					break;
				case 8:
					exit = true;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	private static void printMenu() {
		System.out.println("Menu:"+System.lineSeparator()+
				"1) Connect"+System.lineSeparator()+
				"2) Disconnect"+System.lineSeparator()+
				"3) Stop Notification"+System.lineSeparator()+
				"4) Publish News"+System.lineSeparator()+
				"5) Unsubscribe from topic"+System.lineSeparator()+
				"6) Subscribe topic"+System.lineSeparator()+
				"7) Begin reading news"+System.lineSeparator()+
				"8) Exit"+System.lineSeparator());
		
	}

}
