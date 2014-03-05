package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import databases.MyDBInfo;

public class FillBlankTable extends Database {

	public FillBlankTable(){
		NUM_COLS = 7;
		tableName = "fillblankquestion";
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			if (i==0 || i==1 || i==3 || i==5) table[i] = new ArrayList<Integer>();
			else table[i] = new ArrayList<String>();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);
			while(rs.next()) {
				Integer p_id = rs.getInt("p_id");
				Integer q_id = rs.getInt("q_id");
				String q_text = rs.getString("q_text");
				Integer a_id = rs.getInt("a_id");
				String a_text = rs.getString("a_text");
				Integer position = rs.getInt("position");
				Integer quiz_id = rs.getInt("quiz_id");

				table[0].add(p_id);
				table[1].add(q_id);
				table[2].add(q_text);
				table[3].add(a_id);
				table[4].add(a_text);
				table[5].add(position);
				table[6].add(quiz_id);
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
	
	public void add(Integer q_id, String q_text, Integer a_id, String a_text, Integer position, Integer quiz_id){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = buildAddQuery(q_id, q_text, a_id, a_text, position, quiz_id);
			stmt.executeUpdate(query);
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			Integer i=0;
			if (rs.next())	i = rs.getInt("last_insert_id()");
			table[0].add(i);
			table[1].add(q_id);
			table[2].add(q_text);
			table[3].add(a_id);
			table[4].add(a_text);
			table[5].add(position);
			table[6].add(quiz_id);
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String buildAddQuery(Integer q_id, String q_text, Integer a_id, String a_text, Integer position, Integer quiz_id){
		String query = " INSERT INTO "+tableName;
		query += " (q_id, q_text, a_id, a_text, position, quiz_id)";
		query += " VALUES("+q_id+",\""+q_text+"\","+a_id+",\""+a_text+"\","+position+","+quiz_id+");";
		return query;
	}
	
}
