package users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import databases.FriendTable;
import databases.MessageTable;

import messages.Message;
import models.Quiz;

public class User {
	private int id;
	private String username;
	private byte[] hash, salt;
	ArrayList<Message> messages;
	ArrayList<Integer> friends;
	ArrayList<Message> friendRequests;
	ArrayList<Quiz> quizzes;
	private boolean admin;
	
	public User(int id, String username, byte[] salt, byte[] hash, boolean isAdmin, ArrayList<Integer> friends) {
		this.id = id;
		this.username = username;
		this.admin = isAdmin;
		this.messages = new ArrayList<Message>();
		this.friendRequests = new ArrayList<Message>();
		this.quizzes = new ArrayList<Quiz>();
		this.friends = friends;
		this.salt = salt;
		this.hash = hash;
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
		friends.add(u.getId());
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
		MessageTable.deleteMessage(msg.getId());
	}
	
	public static void addFriend(User one, User two) {
		FriendTable.save(one, two);
		one.addFriend(two);
		two.addFriend(one);
	}
	
	public void removeFriend(User u) {
		int id = u.getId();
		Iterator<Integer> it = friends.iterator();
		while(it.hasNext()) {
			int curid = it.next();
			if(curid == id) {
				it.remove();
				break;
			}
		}
	}
	
	public static void removeFriend(User one, User two) {
		FriendTable.removeFriendship(one, two);
		one.removeFriend(two);
		two.removeFriend(one);
	}
	
	//Getters
	public String getUsername() {return username;}
	public byte[] getSalt() {return salt;}
	public byte[] getHash() {return hash;}
	public boolean isAdmin() {return admin;}
	public int getId() {return id;}
	public ArrayList<Message> getMessages() {return messages;}
	public ArrayList<Integer> getFriends() {return friends;}
	public ArrayList<Message> getFriendRequests() {return friendRequests;}
	
	//Misc
	public boolean equals(User other) {
		if(this.username.equals(other.getUsername())) return true;
		return false;
	}
}
