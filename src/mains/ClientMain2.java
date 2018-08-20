package mains;

import java.util.concurrent.TimeUnit;

import client.Client;
import commons.News;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;

public class ClientMain2 {

	public static void main(String[] args) {
		

				Client client = new Client("SERVER_2");
				
				TopicInterface topic = new Topic("Sport", "VIVA IL PALLONE");
				for(int i=0; i<10; ++i) {

							NewsInterface news = new News(topic, "LUL"+i);
							try {
								TimeUnit.SECONDS.sleep(10);
								System.out.println(i);
								client.Publish(news);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}


			
			
				}
		}

	}


