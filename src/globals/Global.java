package globals;

import databases.Database;

public class Global {
	public static Database database;
	public Global(){
		this.database = new Database();
		this.database.openConnection();
	}
}
