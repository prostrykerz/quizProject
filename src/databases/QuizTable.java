package databases;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizTable extends AbstractDatabase {

	public QuizTable(String tableName) {
		ArrayList<String> columnNames = new ArrayList<String>(Arrays.asList("qId", "qText", "aId", "aText", "idenID"));
		ArrayList<String> columnTypes = new ArrayList<String>(Arrays.asList("qId", "qText", "aId", "aText", "idenID"));
		createTable(tableName,columnNames, columnTypes); 
	}
	
	public void addQuestion(){
		
	}

}
