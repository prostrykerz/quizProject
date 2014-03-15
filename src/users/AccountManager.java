package users;

import java.util.HashMap;
import java.util.HashSet;

import databases.AchievementTable;
import databases.FriendTable;
import databases.UserTable;

import messages.Message;

public class AccountManager {
	HashMap<Integer, User> users;
	public AccountManager() {
		users = UserTable.getUsers();
		if(users == null || users.size() == 0) initializeStarterUsers();
	}
	
	public void addUser(String username, String password) {
		User constructed_user = UserTable.save(username, password, false);
		users.put(constructed_user.getId(),constructed_user);
		AchievementTable.initialize(constructed_user.getId());
	}
	
	public User getUserById(int id) {
		if(users.containsKey(id)) return users.get(id);
		return null;
	}
	
	public void removeUser(User u) {
		for(Integer id : users.keySet()) {
			if(id == u.getId()) {
				users.remove(id);
				break;
			}
		}
	}
	
	public HashSet<User> getUsersIterable() {
		HashSet<User> usersSet = new HashSet<User>();
		for(Integer id : users.keySet()) usersSet.add(users.get(id));
		return usersSet;
	}
	
	public User getUserByUsername(String username) {
		for(Integer id : users.keySet()) {
			if(users.get(id).getUsername().equals(username)) return users.get(id);
		}
		return null;
	}
	
	public boolean userExists(String username) {
		for(Integer id : users.keySet()) {
			if(users.get(id).getUsername().equals(username)) return true;
		}
		return false;
	}
	
	public boolean passwordMatches(String username, String password) {
		for(Integer id : users.keySet()) {
			if(users.get(id).getUsername().equals(username)) return users.get(id).authenticate(password);
		}
		return false;
	}
	
	private void initializeStarterUsers() {
		UserTable.save("andrew", "gloving", true);
		UserTable.save("travis", "gloving", true);
		UserTable.save("adrian", "gloving", true);
		users = UserTable.getUsers();
	}
}
