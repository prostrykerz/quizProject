package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import messages.Message;
import models.Quiz;
import models.QuizHistory;
import users.User;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class QuizHistoryTable extends Database{
	private static String tableName = "history";
	public static void createTable() {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(p_id INT NOT NULL AUTO_INCREMENT, user_id INT, quiz_id INT, score SMALLINT, time INT, PRIMARY KEY (p_id));";
			stmt.executeUpdate(query);
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
	
	public static void save(int uid, int qid, int score, int time) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (user_id, quiz_id, score, time) VALUES (?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setInt(1, uid);
			pstmt.setInt(2, qid);
			pstmt.setInt(3, score);
			pstmt.setInt(4, time);
			pstmt.execute();
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
	
	public static ArrayList<QuizHistory> getUserTakenQuizzes(User u) {
		Connection con = Global.database.getConnection();
		ArrayList<QuizHistory> qhs = new ArrayList<QuizHistory>();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				int id = rs.getInt("user_id");
				if(u.getId() == id) {
					QuizHistory qh = new QuizHistory(u.getId(), rs.getInt("quiz_id"), rs.getInt("score"), rs.getInt("time"));
					qhs.add(qh);
				}
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
		
		return qhs;
	}
	
	public static ArrayList<QuizHistory> getQuizAttempts(int quiz_id) {
		Connection con = Global.database.getConnection();
		ArrayList<QuizHistory> qhs = new ArrayList<QuizHistory>();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName + " WHERE quiz_id = " + quiz_id);
			while(rs.next()) {
				QuizHistory qh = new QuizHistory(rs.getInt("user_id"), rs.getInt("quiz_id"), rs.getInt("score"), rs.getInt("time"));
				qhs.add(qh);
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
		return qhs;
	}
	
	public static ArrayList<QuizHistory> getAllQuizAttempts() {
		Connection con = Global.database.getConnection();
		ArrayList<QuizHistory> qhs = new ArrayList<QuizHistory>();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				QuizHistory qh = new QuizHistory(rs.getInt("user_id"), rs.getInt("quiz_id"), rs.getInt("score"), rs.getInt("time"));
				qhs.add(qh);
			}
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return qhs;
	}
}
