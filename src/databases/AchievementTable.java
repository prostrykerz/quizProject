package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import models.Achievement;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class AchievementTable extends Database{
	private static String tableName = "Achievements";
	public static void createTable() {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(uid INT NOT NULL AUTO_INCREMENT, user_id INT, one BOOLEAN DEFAULT 0, one_time TIMESTAMP, two BOOLEAN DEFAULT 0, two_time TIMESTAMP, three BOOLEAN DEFAULT 0, three_time TIMESTAMP, four BOOLEAN DEFAULT 0, four_time TIMESTAMP, five BOOLEAN DEFAULT 0, five_time TIMESTAMP, six BOOLEAN DEFAULT 0, six_time TIMESTAMP, PRIMARY KEY (uid));";
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
	
	public static void initialize(int id) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (user_id,one,two,three,four,five,six, one_time, two_time,three_time,four_time,five_time,six_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setBoolean(4, false);
			pstmt.setBoolean(5, false);
			pstmt.setBoolean(6, false);
			pstmt.setBoolean(7, false);
			Date now = new Date();
			Timestamp timestamp = new Timestamp(now.getTime());
			pstmt.setTimestamp(8, timestamp);
			pstmt.setTimestamp(9, timestamp);
			pstmt.setTimestamp(10, timestamp);
			pstmt.setTimestamp(11, timestamp);
			pstmt.setTimestamp(12, timestamp);
			pstmt.setTimestamp(13, timestamp);
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
	
	public static ArrayList<Achievement> getAchievements(int id) {
		Connection con = Global.database.getConnection();
		ArrayList<Achievement> achievements = new ArrayList<Achievement>();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName + " WHERE user_id = " + id);
			while(rs.next()) {
				Achievement one = new Achievement(id, 0, rs.getBoolean("one"), rs.getDate("one_time"));
				Achievement two = new Achievement(id, 1, rs.getBoolean("two"), rs.getDate("two_time"));
				Achievement three = new Achievement(id, 2, rs.getBoolean("three"), rs.getDate("three_time"));
				Achievement four = new Achievement(id, 3, rs.getBoolean("four"), rs.getDate("four_time"));
				Achievement five = new Achievement(id, 4, rs.getBoolean("five"), rs.getDate("five_time"));
				Achievement six = new Achievement(id, 5, rs.getBoolean("six"), rs.getDate("six_time"));
				
				achievements.add(one);
				achievements.add(two);
				achievements.add(three);
				achievements.add(four);
				achievements.add(five);
				achievements.add(six);
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
		return achievements;
	}
	
	public static Achievement getAchievement(int uid, String index) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName + " WHERE user_id = " + uid);
			while(rs.next()) {
				int code = -1;
				if(index.equals("one")) code = 0;
				else if(index.equals("two")) code =1;
				else if(index.equals("three")) code =2;
				else if(index.equals("four")) code =3;
				else if(index.equals("five")) code =5;
				else if(index.equals("six")) code = 6;
				Achievement a = new Achievement(uid, code, rs.getBoolean(index), rs.getDate(index + "_time"));
				
				return a;
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
		return null;
	}
	
	public static void awardAchievement(int uid, String index) {
		Connection con = Global.database.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "UPDATE " + tableName + " SET "+index+" = ?, " + index + "_time = ? WHERE user_id = " + uid;
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setBoolean(1, true);
			java.util.Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			pstmt.setTimestamp(2, timestamp);
			pstmt.execute();
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
