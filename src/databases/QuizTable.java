package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import databases.MyDBInfo;

public class QuizTable extends AbstractTableModel {
	
	private static String account = MyDBInfo.MYSQL_USERNAME;
	private static String password = MyDBInfo.MYSQL_PASSWORD; 
	private static String server = MyDBInfo.MYSQL_DATABASE_SERVER; 
	private static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	private ArrayList[] table;
	private static final int NUM_COLS = 4;
	private Connection con;

	public QuizTable(){
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			table[i] = new ArrayList<String>();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM products");
			while(rs.next()) {
				String productid = rs.getString("productid");
				String name = rs.getString("name");
				String imagefile = rs.getString("imagefile");
				String price = rs.getString("price");
				table[0].add(productid);
				table[1].add(name);
				table[2].add(imagefile);
				table[3].add(price);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
