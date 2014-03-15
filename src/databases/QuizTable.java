package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import messages.Message;
import models.Quiz;
import models.QuizHistory;

import users.User;

import databases.MyDBInfo;

public class QuizTable extends Database {

	private static int NUM_COLS = 11;
	private static String tableName = "quizzes";
	
	public QuizTable(){
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			if (i==0 || i==7 || i==8 || i==10) table[i] = new ArrayList<Integer>();
			else if (i>=3 && i<=6) table[i] = new ArrayList<Boolean>();
			else table[i] = new ArrayList<String>();
		}
		Connection con = Global.database.getConnection();
		
		try {
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
				Integer timesTaken = rs.getInt("timesTaken");
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
				table[10].add(timesTaken);
			}
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static Integer addToDatabase(String name, String description, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator, Integer timesTaken, Timestamp createdAt){
		
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = buildAddQuery(name, description, random, onePage, immediateFeedback, practiceMode, score, time, creator, timesTaken, createdAt);
			stmt.executeUpdate(query);
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			Integer i=0;
			if (rs.next())	i = rs.getInt("last_insert_id()");
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return i;
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	private static String buildAddQuery(String name, String description, Boolean random, Boolean onePage, Boolean immediateFeedback, Boolean practiceMode, Integer score, Integer time, String creator, Integer timesTaken, Timestamp createdAt){
		String query = " INSERT INTO "+tableName;
		query += " (name, description, random, onePage, immediateFeedback, practiceMode, score, time, creator, timesTaken, createdAt)";
		query += " VALUES(\""+name+"\",\""+description+"\","+random+","+onePage+","+immediateFeedback+","+practiceMode+","+score+","+time+",\""+creator+"\","+timesTaken+",\""+createdAt+"\");";
		return query;
	}
	
	public static void deleteQuiz(int id) {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE p_id = " + id);
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteUserQuizzes(String username) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE creator = \"" + username + "\"");
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Quiz> getQuizzes(String username) {
		Connection con = Global.database.getConnection();
		try {
			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName + " WHERE creator = '" + username + "'");
			while(rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("p_id"));
				quizzes.add(quiz);
			}
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return quizzes;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<Quiz> getAllQuizzes() {
		Connection con = Global.database.getConnection();
		try {
			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("p_id"));
				quizzes.add(quiz);
			}
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return quizzes;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<Quiz> getTopQuizzes(int numberOfQuizzes) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		if(numberOfQuizzes <= 0) return quizzes;
		ArrayList<Quiz> allquizzes = QuizTable.getAllQuizzes();
		Collections.sort(allquizzes, new Comparator<Quiz>() {
			@Override
			public int compare(Quiz q1, Quiz q2) {
				// TODO Auto-generated method stub
				return q2.getTimesTaken() - q1.getTimesTaken();
			}
		});
		for(int i = 0; i < numberOfQuizzes; i++) {
			if(i == allquizzes.size()) break;
			quizzes.add(allquizzes.get(i));
		}
		return quizzes;
	}
	
	public static void incrementTimesTaken(int qid) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("UPDATE "+ tableName + " SET timesTaken = timesTaken + 1 WHERE p_id = " + qid);
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearTimesTaken(int qid) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("UPDATE "+ tableName + " SET timesTaken = 0 WHERE p_id = " + qid);
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getNumQuizzes() {
		Connection con = Global.database.getConnection();
		int count = 0;
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
			while (rs.next()) {
				count = rs.getInt(1);
			}
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	

	public static void setTimeScoreTimesTaken(int p_id, int time, int score, int timesTaken){
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeQuery("UPDATE "+ tableName + " SET time="+time+", score="+score+", timesTaken="+score+" WHERE p_id = " + p_id);
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
