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
		assertEquals(9,quizTable.getColumnCount());
		assertEquals(2,quizTable.getRowCount());
		System.out.println(quizTable.getTable()[1].get(1));
	}
}
