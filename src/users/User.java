package users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import messages.Message;
import models.Quiz;

public class User {
	private static int IDGEN = 0;
	private String username;
	private int id;
	private byte[] hash, salt;
	ArrayList<Message> messages;
	HashSet<User> friends;
	ArrayList<Message> friendRequests;
	ArrayList<Quiz> quizzes;
	private boolean admin;
	
	public User(String username, String password, boolean isAdmin) {
		this.username = username;
		this.admin = isAdmin;
		this.messages = new ArrayList<Message>();
		this.friendRequests = new ArrayList<Message>();
		this.quizzes = new ArrayList<Quiz>();
		this.friends = new HashSet<User>();
		this.id = IDGEN++;
		try{
			this.hash = hashPassword(password);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public User(String username, String password) {
		this(username, password, false);
	}
	
	private byte[] hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		this.salt = salt;
		return digest.digest(password.getBytes("UTF-8"));
	}
	
	public boolean authenticate(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			byte[] newhash = digest.digest(password.getBytes("UTF-8"));
			return Arrays.equals(hash,newhash);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean hasFriendRequestFrom(User u) {
		for(Message fr : friendRequests) {
			if(fr.getSender().equals(u)) return true;
		}
		return false;
	}
	
	//Setters
	public void addMessage(Message m) {
		messages.add(m);
	}
	
	public void addFriendRequest(Message m) {
		friendRequests.add(m);
	}
	
	public void addFriend(User u) {
		if(!friends.contains(u)) {
			friends.add(u);
		}
	}
	
	public void addQuiz(Quiz q) {
		quizzes.add(q);
	}
	
	public void deleteFriendRequest(User requester) {
		Message msg = null;
		for(Message m : friendRequests) {
			if(m.getSender().equals(requester)) {
				msg = m;
				break;
			}
		}
		friendRequests.remove(msg);
	}
	
	//Getters
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public HashSet<User> getFriends() {
		return friends;
	}
	
	public ArrayList<Message> getFriendRequests() {
		return friendRequests;
	}
	
	//Misc
	public boolean equals(User other) {
		if(this.username.equals(other.getUsername())) return true;
		return false;
	}
}
