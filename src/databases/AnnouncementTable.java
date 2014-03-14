package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class AnnouncementTable extends Database{
	private static String tableName = "Announcements";
	
	public static void createTable() {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(id INT NOT NULL AUTO_INCREMENT, text VARCHAR(255),  PRIMARY KEY (id));";
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
	
	public static int save(String text) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (text) VALUES (?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setString(1, text);
			pstmt.execute();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
			int count = 0;
			while(rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			pstmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return count;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void delete(int id) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE id = " + id);
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
