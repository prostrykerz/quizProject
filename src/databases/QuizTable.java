package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import databases.MyDBInfo;

public class QuizTable extends Database {

	private static int NUM_COLS = 9;
	private static String tableName = "quizzes";
	
	public QuizTable(){
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			if (i==0 || i==6 || i==7) table[i] = new ArrayList<Integer>();
			else if (i>=2 && i<=5) table[i] = new ArrayList<Boolean>();
			else table[i] = new ArrayList<String>();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				Integer p_id = rs.getInt("p_id");
				String name = rs.getString("name");
				Boolean random = rs.getBoolean("random");
				Boolean onePage = rs.getBoolean("onePage");
				Boolean immediateFeedback = rs.getBoolean("immediateFeedback");
				Boolean practiceMode = rs.getBoolean("practiceMode");
				Integer score = rs.getInt("score");
				Integer time = rs.getInt("time");
				String creator = rs.getString("creator");
				table[0].add(p_id);
				table[1].add(name);
				table[2].add(random);
				table[3].add(onePage);
				table[4].add(immediateFeedback);
				table[5].add(practiceMode);
				table[6].add(score);
				table[7].add(time);
				table[8].add(creator);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Integer add(String name, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = buildAddQuery(name, random, onePage, immediateFeedback, practiceMode, score, time, creator);
			stmt.executeUpdate(query);
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			Integer i=0;
			if (rs.next())	i = rs.getInt("last_insert_id()");
			table[0].add(i);
			table[1].add(name);
			table[2].add(random);
			table[3].add(onePage);
			table[4].add(immediateFeedback);
			table[5].add(practiceMode);
			table[6].add(score);
			table[7].add(time);
			table[8].add(creator);
			con.close();
			return i;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addToDatabase(String name, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = buildAddQuery(name, random, onePage, immediateFeedback, practiceMode, score, time, creator);
			stmt.executeUpdate(query);
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String buildAddQuery(String name, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator){
		String query = " INSERT INTO "+tableName;
		query += " (name, random, onePage, immediateFeedback, practiceMode, score, time, creator)";
		query += " VALUES(\""+name+"\","+random+","+onePage+","+immediateFeedback+","+practiceMode+","+score+","+time+",\""+creator+"\");";
		return query;
	}
	
}
