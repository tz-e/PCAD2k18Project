package testPackage;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import commons.News;
import commons.NewsInterface;
import commons.Topic;

class NewsTest {
	
	@Test
	void createNewNewsReturnsOk() {
		new News(new Topic("Calcio", "Pallone"), "Tizio ha vinto l'invincibile");
	}
	@Test
	void createNewNewsNullTopicThrowsException(){
		assertThrows(IllegalArgumentException.class, ()->new News(null, "Pallone"));
	}
	@Test
	void createNewNewsNullTextThrowsException(){
		assertThrows(IllegalArgumentException.class, ()->new News(new Topic("Calcio", "Pallone"), null));
	}
}
