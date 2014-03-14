package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import users.User;


public class FriendTable extends Database {

	private static String tableName = "Friends";
	
	public static void createTable() {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(user_one INT, user_two INT);";
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
	
	public static void save(User one, User two) {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (user_one, user_two) VALUES (?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setInt(1, one.getId());
			pstmt.setInt(2, two.getId());
			pstmt.execute();
			stmt.close();
			pstmt.close();
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
	
	public static ArrayList<Integer> getFriends(int id) {
		ArrayList<Integer> friends = new ArrayList<Integer>();

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				int id_one = rs.getInt("user_one");
				int id_two = rs.getInt("user_two");
				if(id_one == id) friends.add(id_two);
				else if(id_two == id) friends.add(id_one);
			}
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return friends;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void removeFriendship(User one, User two) {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE (user_one = " + one.getId() + " AND user_two = " + two.getId() + ") OR (user_one = " + two.getId() + " AND user_two = " + one.getId() + ")");
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
	
	public static void deleteUserFriends(int id) {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE user_one = " + id + " OR user_two = " + id);
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
