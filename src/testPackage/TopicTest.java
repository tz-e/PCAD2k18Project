package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import commons.Topic;

class TopicTest {

	@Test
	void createNewTopicReturnsOk() {
		new Topic("Uhm", "Yeah");
	}
	@Test
	void createNewNewsNullTopicThrowsException(){
		assertThrows(IllegalArgumentException.class, ()->new Topic(null, "Pallone"));
	}
	@Test
	void createNewNewsNullTextThrowsException(){
		assertThrows(IllegalArgumentException.class, ()->new Topic("Uhm", null));
	}

}
