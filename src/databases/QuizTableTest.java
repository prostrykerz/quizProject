package databases;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QuizTableTest {

	private QuizTable quizTable;

	@Before
	public void setUp() throws Exception {
		this.quizTable = new QuizTable();
	}

	@Test
	public void test1(){
//		assertEquals(9,quizTable.getColumnCount());
//		assertEquals(2,quizTable.getRowCount());
//		System.out.println(quizTable.getTable()[1].get(1));
	}

	@Test
	public void test2(){
		QuizTable.addToDatabase("quiz 4", true, false, false, true, 5, 3000, "Adrian");
		System.out.println(quizTable.getTable()[0].size());
		
		for (int i=0; i<quizTable.getTable()[1].size(); i++){
			System.out.println(String.valueOf(quizTable.getTable()[0].get(i)));
		}
	}
}
