package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import messages.Message;

import users.User;

public class MessageTable extends Database{
	private static String tableName = "Messages";
	
	public static void createTable() {

		Connection con = Global.database.getConnection();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(id INT NOT NULL AUTO_INCREMENT, sender INT, receiver INT, message VARCHAR(200), PRIMARY KEY (id));";
			stmt.executeUpdate(query);
			stmt.close();
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Message save(User sender, User receiver, String message) {

		Connection con = Global.database.getConnection();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (sender, receiver, message) VALUES (?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setInt(1, sender.getId());
			pstmt.setInt(2, receiver.getId());
			pstmt.setString(3, message);
			pstmt.execute();
			Message m = lastMessage();
			stmt.close();
			pstmt.close();
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return m;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Message> getMessages(int id) {
		ArrayList<Message> messages = new ArrayList<Message>();

		Connection con = Global.database.getConnection();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				int receiver = rs.getInt("receiver");
				if(receiver == id) {
					String text = rs.getString("message");
					Message message = new Message(rs.getInt("id"),UserTable.getUser(rs.getInt("sender")), UserTable.getUser(receiver), text);
					messages.add(message);
				}
			}
			rs.close();
			stmt.close();
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	private static Message lastMessage() {

		Connection con = Global.database.getConnection();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			rs.last();
			Message message = new Message(rs.getInt("id"),UserTable.getUser(rs.getInt("sender")), UserTable.getUser(rs.getInt("receiver")), rs.getString("message"));
			rs.close();
			stmt.close();
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return message;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deleteMessage(int id) {
		Connection con = Global.database.getConnection();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE id = " + id);
			stmt.close();
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUserMessages(int id) {
		Connection con = Global.database.getConnection();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE sender = " + id + " OR receiver = " + id);
			stmt.close();
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
