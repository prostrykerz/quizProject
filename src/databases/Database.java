package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import databases.MyDBInfo;

public class Database extends AbstractTableModel {
	
	protected static String account = MyDBInfo.MYSQL_USERNAME;
	protected static String password = MyDBInfo.MYSQL_PASSWORD; 
	protected static String server = MyDBInfo.MYSQL_DATABASE_SERVER; 
	protected static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	protected static ArrayList[] table;
	protected static int NUM_COLS;
	public Connection con;
	protected String tableName;
	
	
	public void openConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean connectionClosed(){
		try {
			return con.isClosed();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return con;
	}
	
	@Override
	public int getRowCount() {
		return table[0].size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COLS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return table[columnIndex].get(rowIndex);
	}
	
	public ArrayList<String>[] getTable(){
		return table;
	}
	
}
