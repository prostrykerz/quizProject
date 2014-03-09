package users;

import java.util.HashSet;

import databases.UserTable;

import messages.Message;

public class AccountManager {
	HashSet<User> users;
	public AccountManager() {
		UserTable.createTable();
		users = UserTable.getUsers();
		if(users.size() == 0) initializeStarterUsers();
	}
	
	public void addUser(User user) {
		users.add(user);
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
		User andrew = new User("andrew","gloving");
		User travis = new User("travis","poop");
		User adrian = new User("adrian","poop");
		UserTable.save(andrew);
		UserTable.save(travis);
		UserTable.save(adrian);
		users = UserTable.getUsers();
	}
}
