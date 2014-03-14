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
			query += "(uid INT NOT NULL AUTO_INCREMENT, user_id INT, one BOOLEAN, one_time TIMESTAMP, two BOOLEAN, two_time TIMESTAMP, three BOOLEAN, three_time TIMESTAMP, four BOOLEAN, four_time TIMESTAMP, five BOOLEAN, five_time TIMESTAMP, six BOOLEAN, six_time TIMESTAMP, PRIMARY KEY (uid));";
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
	
	public static Achievement[] getAchievements(int id) {
		Connection con = Global.database.getConnection();
		Achievement[] achievements = new Achievement[6];
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
				
				achievements[0] = one;
				achievements[1] = two;
				achievements[2] = three;
				achievements[3] = four;
				achievements[4] = five;
				achievements[5] = six;
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
	
	public static void awardAchievement(int uid, String index) {
		Connection con = Global.database.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("UPDATE " + tableName + " SET " + index + " = 1 WHERE uid = " + uid);
			stmt.close();
			String query = "UDPATE " + tableName + " ("+index+", " + index + "_time at) VALUES (?,?)";
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
