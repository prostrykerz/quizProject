package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

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
			query += "(p_id INT NOT NULL AUTO_INCREMENT, user_id INT, quiz_id INT, score SMALLINT, time INT, at TIMESTAMP, PRIMARY KEY (p_id));";
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
			String query = "INSERT INTO " + tableName + " (user_id, quiz_id, score, time, at) VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setInt(1, uid);
			pstmt.setInt(2, qid);
			pstmt.setInt(3, score);
			pstmt.setInt(4, time);
			java.util.Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			pstmt.setTimestamp(5, timestamp);
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
					QuizHistory qh = new QuizHistory(u.getId(), rs.getInt("quiz_id"), rs.getInt("score"), rs.getInt("time"), rs.getTimestamp("at"));
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
				QuizHistory qh = new QuizHistory(rs.getInt("user_id"), rs.getInt("quiz_id"), rs.getInt("score"), rs.getInt("time"), rs.getTimestamp("at"));
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
				QuizHistory qh = new QuizHistory(rs.getInt("user_id"), rs.getInt("quiz_id"), rs.getInt("score"), rs.getInt("time"), rs.getTimestamp("at"));
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
	
	public static ArrayList<QuizHistory> getUserAttemptsOnQuiz(User u, int qid) {
		Connection con = Global.database.getConnection();
		ArrayList<QuizHistory> qhs = new ArrayList<QuizHistory>();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			System.out.println("SELECT * FROM "+ tableName + " WHERE user_id = " + u.getId() + " AND quiz_id = " + qid);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName + " WHERE user_id = " + u.getId() + " AND quiz_id = " + qid);
			while(rs.next()) {
				System.out.println("doisodf");
				QuizHistory qh = new QuizHistory(u.getId(), qid, rs.getInt("score"), rs.getInt("time"), rs.getTimestamp("at"));
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
}
