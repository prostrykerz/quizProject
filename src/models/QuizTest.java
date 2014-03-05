package models;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class QuizTest {

	
	@Before
	public void setUp() throws Exception {
		SingleResponseTextQuestion q1 = new SingleResponseTextQuestion("how are you?", new ArrayList<String>(Arrays.asList("good")), 1);
		SingleResponseTextQuestion q2 = new SingleResponseTextQuestion("do you like dogs?", new ArrayList<String>(Arrays.asList("yes")), 2);
		ArrayList<Question> questionArr = new ArrayList<Question>();
		questionArr.add(q1);
		questionArr.add(q2);
		String title = "First Ever Quiz";
		String description = "this is cool if it works...";
		boolean isRandom = false;
		boolean isOnePage = true;
		boolean hasImmediateFeedback = false;
		boolean practiceMode = false;
		String creator = "Travis";
		Quiz quiz = new Quiz(questionArr, title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, creator);
	}

	@Test
	public void test1(){
		Quiz.loadXML("");
	}

}
