package globals;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import users.AccountManager;
import admin.Announcement;

import databases.AchievementTable;
import databases.AnnouncementTable;
import databases.Database;
import databases.DatabaseUtils;
import databases.FriendTable;
import databases.MessageTable;
import databases.UserTable;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	AccountManager manager;
    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
    	new Global();
    	if (Global.database.connectionClosed()) System.out.println("ok it's fine");
    	else System.out.println("fuck our lives.");
    	createTables();
    	
    	manager = new AccountManager();
    	
        context.setAttribute("announcements", new ArrayList<Announcement>());
        context.setAttribute("manager", manager);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	Global.database.closeConnection();
    	try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            // again failure, not much you can do
        }
    }
	
    private void createTables() {
    	//TEsting
//    	DatabaseUtils.dropTable("Users");
//    	DatabaseUtils.dropTable("Friends");
//    	DatabaseUtils.dropTable("Messages");
//    	DatabaseUtils.dropTable("Announcements");
    	
    	//KEep
    	UserTable.createTable();
    	FriendTable.createTable();
    	MessageTable.createTable();
    	AnnouncementTable.createTable();
    	AchievementTable.createTable();
    }
    
    private void killProcesses() {
    	
    }
}
