package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class AchievementTable extends Database{
	private static String tableName = "Achievements";
	public static void createTable() {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(uid INT NOT NULL AUTO_INCREMENT, username CHAR(64), one BOOLEAN, two BOOLEAN, three BOOLEAN, four BOOLEAN, five BOOLEAN, six BOOLEAN, PRIMARY KEY (uid));";
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
	
	public static boolean[] getAchievements(int id) {
		Connection con = Global.database.getConnection();
		boolean[] achievements = new boolean[6];
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				achievements[0] = rs.getBoolean("one");
				achievements[1] = rs.getBoolean("two");
				achievements[2] = rs.getBoolean("three");
				achievements[3] = rs.getBoolean("four");
				achievements[4] = rs.getBoolean("five");
				achievements[5] = rs.getBoolean("six");
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
