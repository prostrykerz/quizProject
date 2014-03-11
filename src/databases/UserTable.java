package databases;

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

import users.User;

import databases.MyDBInfo;

public class UserTable extends Database {
	
	private static String tableName = "Users";
	//1 = username
	//2 = salt
	//3 = hash
	//4 = admin
	
	public static void createTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);			
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(id INT NOT NULL AUTO_INCREMENT, username CHAR(64), salt BLOB, hash BLOB, admin BOOLEAN, PRIMARY KEY (id));";
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static User save(String username, String user_password, boolean admin){
		try {
			byte[] salt = createSalt();
			byte[] hash = createHash(salt, user_password);
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
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
			con.close();
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
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
		try {
			HashSet<User> users = new HashSet<User>();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				User user = rsToUser(rs);
				users.add(user);
			}
			con.close();
			return users;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new HashSet<User>();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User getUser(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			User user;
			while(rs.next()) {
				if(id == rs.getInt("id")) {
					user = rsToUser(rs);
					return user;
				}
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static User lastUser() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			rs.last();
			User user = rsToUser(rs);
			con.close();
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
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
			return new User(id, username, salt, hash, admin, friends);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
