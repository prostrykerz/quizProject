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
		SingleResponsePicQuestion q3 = new SingleResponsePicQuestion("would you like food?", "www.google.com", new ArrayList<String>(Arrays.asList("yes")), 1);
		SingleResponsePicQuestion q4 = new SingleResponsePicQuestion("what is this country?", "www.google.com", new ArrayList<String>(Arrays.asList("north korea")), 2);
		FillBlankQuestion q5 = new FillBlankQuestion("i like x.", new ArrayList<String>(Arrays.asList("dogs")), 1);
		MultiChoiceTextQuestion q6 = new MultiChoiceTextQuestion("who is on the team?", new ArrayList<String>(Arrays.asList("me")), new ArrayList<String>(Arrays.asList("me", "you")), 1);
		MultiChoicePicQuestion q7 = new MultiChoicePicQuestion("where do we live?", "www.google.com", new ArrayList<String>(Arrays.asList("here")), new ArrayList<String>(Arrays.asList("here", "there")), 1);
		
		ArrayList<Question> questionArr = new ArrayList<Question>();
		questionArr.add(q1);
		questionArr.add(q2);
		questionArr.add(q3);
		questionArr.add(q4);
		questionArr.add(q5);
		questionArr.add(q6);
		questionArr.add(q7);
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
//		Quiz.loadXML("");
	}

}
