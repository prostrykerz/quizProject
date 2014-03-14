package globals;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.util.codec.binary.Base64;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import users.AccountManager;
import users.FriendUpdate;
import users.User;
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
	HashMap<String, Integer> cookieMap;
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
    	createTables();

    	manager = new AccountManager();
    	cookieMap = new HashMap<String, Integer>();
    	for(User u : manager.getUsersIterable()) {
    		cookieMap.put(getToken(u), u.getId());
    	}
    	
        context.setAttribute("announcements", new ArrayList<Announcement>());
        context.setAttribute("manager", manager);
        context.setAttribute("cookieMap", cookieMap);
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
//    	DatabaseUtils.dropTable("Achievements");
    	
    	//KEep
    	UserTable.createTable();
    	FriendTable.createTable();
    	MessageTable.createTable();
    	AnnouncementTable.createTable();
    	AchievementTable.createTable();
    }
    
    public String getToken(User u) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			String saltString = u.getId() + "SUPER SECRET SALT VALUE";
			digest.update(saltString.getBytes("UTF-8"));
			byte[] hash = digest.digest();
			return Base64.encodeBase64String(hash);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
