package commons;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

class Reader implements Runnable {
	   private final ConcurrentLinkedQueue<NewsInterface> queue;
	   Reader(ConcurrentLinkedQueue<NewsInterface> q) { queue = q; }
	   public void run() {
	     try {
	       while (true) { 
	    	   TimeUnit.SECONDS.sleep(10);
	    	   if(!queue.isEmpty())
	    		   while(!queue.isEmpty()) {
	    	    	   NewsInterface n= queue.poll(); 
	    	    	   System.out.println("Topic: "+n.GetTopic()+ "\\n Testo: "+n.GetText());
	    		   }

	    	   }
	     } catch (InterruptedException ex) {
	    	 ex.printStackTrace();
	     }
	   }
	 }
