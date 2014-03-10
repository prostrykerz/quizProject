package databases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import models.Question;

import org.junit.Before;
import org.junit.Test;


public class QuizModelTableTest {
	
	private QuizModelTable quizModelTable;

	@Before
	public void setUp() throws Exception {
		this.quizModelTable = new QuizModelTable(1);
	}

	@Test
	public void test1(){
		ArrayList <Question> qArr = this.quizModelTable.getQuestions();
		for (int i=0; i<qArr.size(); i++){
			System.out.println(qArr.get(i).getQuestion());
		}
	}
}
