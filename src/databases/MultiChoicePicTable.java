package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import databases.MyDBInfo;

public class MultiChoicePicTable extends Database {
	

	public MultiChoicePicTable(){
		NUM_COLS = 8;
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			if (i==0 || i==1 || i==4 || i==7) table[i] = new ArrayList<Integer>();
			else if(i==6) table[i] = new ArrayList<Boolean>();
			else table[i] = new ArrayList<String>();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM multichoicepicquestion");
			while(rs.next()) {
				Integer p_id = rs.getInt("p_id");
				Integer q_id = rs.getInt("q_id");
				String q_text = rs.getString("q_text");
				String q_url = rs.getString("q_url");
				Integer a_id = rs.getInt("a_id");
				String a_text = rs.getString("a_text");
				Boolean a_correct = rs.getBoolean("a_correct");
				Integer position = rs.getInt("position");

				table[0].add(p_id);
				table[1].add(q_id);
				table[2].add(q_text);
				table[3].add(q_url);
				table[4].add(a_id);
				table[5].add(a_text);
				table[6].add(a_correct);
				table[7].add(position);

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
	
}
