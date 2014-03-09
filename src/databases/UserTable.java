package databases;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.table.AbstractTableModel;

import users.User;

import databases.MyDBInfo;

public class UserTable extends Database {
	
	private static String tableName = "Users";
	private static final int NUM_COLS = 4;
	//1 = username
	//2 = salt
	//3 = hash
	//4 = admin

//	public UserTable(){
//		table = new ArrayList[NUM_COLS];
//		for(int i=0; i<NUM_COLS; i++){
//			if (i ==0) table[i] = new ArrayList<String>();
//			else if(i != 4)table[i] = new ArrayList<Byte[]>();
//			else table[i] = new ArrayList<Boolean>();
//		}
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
//			Statement stmt = con.createStatement();
//			stmt.executeQuery("USE " + database);
//			
//			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
//			int rowcount = 0;
//			if (rs.last()) {
//			  rowcount = rs.getRow();
//			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
//			}
//			if(rowcount == 0) initializeStarterUsers();
//			while(rs.next()) {
//				String username = rs.getString("username");
//				Blob saltblob = rs.getBlob("salt");
//				byte[] salt = saltblob.getBytes(1, (int) saltblob.length());
//				Blob hashblob = rs.getBlob("hash");
//				byte[] hash = hashblob.getBytes(1, (int) hashblob.length());
//				boolean admin = rs.getBoolean("admin");
//				table[0].add(username);
//				table[1].add(salt);
//				table[2].add(hash);
//				table[3].add(admin);
//			}
//			con.close();
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void createTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "CREATE TABLE IF NOT EXISTS " + tableName;
			query += "(username CHAR(64), salt BLOB, hash BLOB, admin BOOLEAN);";
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(User user){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = "INSERT INTO " + tableName + " (username, salt, hash, admin) VALUES (?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setString(1, user.getUsername());
			pstmt.setBytes(2, user.getSalt());
			pstmt.setBytes(3, user.getHash());
			pstmt.setBoolean(4, user.isAdmin());
			pstmt.execute();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
				String username = rs.getString("username");
				Blob saltblob = rs.getBlob("salt");
				byte[] salt = saltblob.getBytes(1, (int) saltblob.length());
				Blob hashblob = rs.getBlob("hash");
				byte[] hash = hashblob.getBytes(1, (int) hashblob.length());
				boolean admin = rs.getBoolean("admin");
				User user = new User(username, salt, hash, admin);
				users.add(user);
			}
			con.close();
			return users;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
