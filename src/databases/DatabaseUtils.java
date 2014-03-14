package databases;

import globals.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class DatabaseUtils extends Database{
	
	public static void dropTable(String tableName) {
		Connection con = Global.database.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
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
