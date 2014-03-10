package globals;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import users.AccountManager;

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
    	
    	createTables();
    	
    	manager = new AccountManager();
        
        context.setAttribute("manager", manager);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
    private void createTables() {
    	//TEsting
    	DatabaseUtils.dropTable("Users");
    	DatabaseUtils.dropTable("Friends");
    	DatabaseUtils.dropTable("Messages");
    	
    	//KEep
    	UserTable.createTable();
    	FriendTable.createTable();
    	MessageTable.createTable();
    }
}
