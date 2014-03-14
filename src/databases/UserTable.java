package databases;

import globals.Global;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.table.AbstractTableModel;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import messages.Message;
import models.Quiz;

import users.User;

import databases.MyDBInfo;

public class UserTable extends Database {
	
	private static String tableName = "Users";
	//1 = username
	//2 = salt
	//3 = hash
	//4 = admin
	
	public static void createTable() {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(id INT NOT NULL AUTO_INCREMENT, username CHAR(64), salt BLOB, hash BLOB, admin BOOLEAN, PRIMARY KEY (id));";
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
	
	public static User save(String username, String user_password, boolean admin){

		Connection con = Global.database.getConnection();
		try {
			byte[] salt = createSalt();
			byte[] hash = createHash(salt, user_password);
			
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (username, salt, hash, admin) VALUES (?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setBytes(2, salt);
			pstmt.setBytes(3, hash);
			pstmt.setBoolean(4, admin);
			pstmt.execute();
			User user = lastUser();
			stmt.close();
			pstmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static byte[] createSalt() {
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);
		return salt;
	}
	
	private static byte[] createHash(byte[] salt, String password) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			return digest.digest(password.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static HashSet<User> getUsers() {
		Connection con = Global.database.getConnection();
		HashSet<User> users = new HashSet<User>();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				User user = rsToUser(rs);
				users.add(user);
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
		return users;
	}
	
	public static User getUser(int id) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			User user = null;
			while(rs.next()) {
				if(id == rs.getInt("id")) {
					user = rsToUser(rs);
					break;
				}
			}
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User getUser(String creator) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName + " WHERE username = '" + creator + "'");
			User user = null;
			while(rs.next()) {
				user = rsToUser(rs);
				break;
			}
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static User lastUser() {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			rs.last();
			User user = rsToUser(rs);
			rs.close();
			stmt.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static User rsToUser(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			Blob saltblob = rs.getBlob("salt");
			byte[] salt = saltblob.getBytes(1, (int) saltblob.length());
			Blob hashblob = rs.getBlob("hash");
			byte[] hash = hashblob.getBytes(1, (int) hashblob.length());
			boolean admin = rs.getBoolean("admin");
			ArrayList<Integer> friends = FriendTable.getFriends(id);
			System.out.println("1");
			ArrayList<Message> messages = MessageTable.getMessages(id);
			System.out.println("2");
			ArrayList<Quiz> quizzes = QuizTable.getQuizzes(username);
			System.out.println("3");
			boolean[] achievements = AchievementTable.getAchievements(id);
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return new User(id, username, salt, hash, admin, messages, quizzes, friends, achievements);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deleteUser(int id) {

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
	
	public static void makeAdmin(int id) {

		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("UPDATE " + tableName + " SET admin = 1 WHERE id = " + id);
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
