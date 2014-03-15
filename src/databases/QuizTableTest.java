package databases;


import static org.junit.Assert.*;

import globals.Global;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class QuizTableTest {

	private QuizTable quizTable;

	@Before
	public void setUp() throws Exception {

		Global g = new Global();
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
		
		System.out.println(quizTable.getTable()[0].size());
		for(int j = 0; j < 10; j++) {
			QuizTable.addToDatabase("First Quiz", "description needed", true, false, false, true, 5, 3000, "adrian", 0, new Timestamp((new Date()).getTime()));
			QuizTable.addToDatabase("First Quiz2", "description needed", true, false, false, true, 5, 3000, "andrew", 0, new Timestamp((new Date()).getTime()));
			QuizTable.addToDatabase("First Quiz3", "description needed", true, false, false, true, 5, 3000, "travis", 0, new Timestamp((new Date()).getTime()));
		}
		for (int i=0; i<quizTable.getTable()[1].size(); i++){
			System.out.println(String.valueOf(quizTable.getTable()[0].get(i)));
		}
	}
}
