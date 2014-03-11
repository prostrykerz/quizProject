package users;

import java.util.HashSet;

import databases.FriendTable;
import databases.UserTable;

import messages.Message;

public class AccountManager {
	HashSet<User> users;
	public AccountManager() {
		users = UserTable.getUsers();
		if(users == null || users.size() == 0) initializeStarterUsers();
	}
	
	public void addUser(String username, String password) {
		User constructed_user = UserTable.save(username, password, false);
		users.add(constructed_user);
	}
	
	public User getUserByUsername(String username) {
		for(User user: users) {
			if(user.getUsername().equals(username)) return user;
		}
		return null;
	}
	
//	public User getUserById(int id) {
//		for(User user : users) {
//			if(user.getId() == id) return user;
//		}
//		return null;
//	}
	
	public boolean userExists(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) return true;
		}
		return false;
	}
	
	public boolean passwordMatches(String username, String password) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user.authenticate(password);
			}
		}
		return false;
	}
	
	public HashSet<User> getUsers() {
		return users;
	}
	
	private void initializeStarterUsers() {
		UserTable.save("andrew", "gloving", true);
		UserTable.save("travis", "gloving", true);
		UserTable.save("adrian", "gloving", true);
		users = UserTable.getUsers();
	}
}
