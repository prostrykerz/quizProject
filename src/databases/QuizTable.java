package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import users.User;

import databases.MyDBInfo;

public class QuizTable extends Database {

	private static int NUM_COLS = 10;
	private static String tableName = "quizzes";
	
	public QuizTable(){
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			if (i==0 || i==7 || i==8) table[i] = new ArrayList<Integer>();
			else if (i>=3 && i<=6) table[i] = new ArrayList<Boolean>();
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
				String description = rs.getString("description");
				Boolean random = rs.getBoolean("random");
				Boolean onePage = rs.getBoolean("onePage");
				Boolean immediateFeedback = rs.getBoolean("immediateFeedback");
				Boolean practiceMode = rs.getBoolean("practiceMode");
				Integer score = rs.getInt("score");
				Integer time = rs.getInt("time");
				String creator = rs.getString("creator");
				table[0].add(p_id);
				table[1].add(name);
				table[2].add(description);
				table[3].add(random);
				table[4].add(onePage);
				table[5].add(immediateFeedback);
				table[6].add(practiceMode);
				table[7].add(score);
				table[8].add(time);
				table[9].add(creator);
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

	public static Integer addToDatabase(String name, String description, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = buildAddQuery(name, description, random, onePage, immediateFeedback, practiceMode, score, time, creator);
			stmt.executeUpdate(query);
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			Integer i=0;
			if (rs.next())	i = rs.getInt("last_insert_id()");
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
	
	private static String buildAddQuery(String name, String description, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator){
		String query = " INSERT INTO "+tableName;
		query += " (name, description, random, onePage, immediateFeedback, practiceMode, score, time, creator)";
		query += " VALUES(\""+name+"\",\""+description+"\","+random+","+onePage+","+immediateFeedback+","+practiceMode+","+score+","+time+",\""+creator+"\");";
		return query;
	}
	
	public static void deleteUserQuizzes(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE creator = \"" + username + "\"");
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
