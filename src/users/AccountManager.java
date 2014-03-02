package users;

import java.util.HashSet;

public class AccountManager {
	HashSet<User> users;
	public AccountManager() {
		users = new HashSet<User>();
		initializeStarterUsers();
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public User getUserById(int id) {
		for(User user : users) {
			if(user.getId() == id) return user;
		}
		return null;
	}
	
	public boolean userExists(String username) {
		for(User user : users) {
			if(user.getUsername() == username) return true;
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
		users.add(andrew);
		users.add(travis);
		users.add(adrian);
	}
}
