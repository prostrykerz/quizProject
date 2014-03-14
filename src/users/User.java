package users;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import databases.AchievementTable;
import databases.FillBlankTable;
import databases.FriendTable;
import databases.MessageTable;
import databases.MultiChoicePicTable;
import databases.MultiChoiceTextTable;
import databases.QuizTable;
import databases.SingleResponsePicTable;
import databases.SingleResponseTextTable;
import databases.UserTable;

import messages.Message;
import models.Achievement;
import models.Quiz;

public class User {
	private int id;
	private String username;
	private byte[] hash, salt;
	private ArrayList<Message> messages;
	private ArrayList<Integer> friends;
	private ArrayList<Message> friendRequests;
	private ArrayList<Quiz> quizzes;
	boolean[] achievements;
	private boolean admin;
	
	public User(int id, String username, byte[] salt, byte[] hash, boolean isAdmin, ArrayList<Message> messages, ArrayList<Quiz> quizzes, ArrayList<Integer> friends, boolean[] achievements) {
		this.id = id;
		this.username = username;
		this.admin = isAdmin;
		this.messages = messages;
		this.friendRequests = new ArrayList<Message>();
		this.quizzes = quizzes;
		this.friends = friends;
		this.salt = salt;
		this.hash = hash;
		this.achievements = achievements;
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
			if(fr.getSender() == u.getId()) return true;
		}
		return false;
	}
	
	public void makeAdmin() {
		UserTable.makeAdmin(id);
		admin = true;
	}
	
	public boolean ownsQuiz(int id) {
		for(Quiz q : quizzes) {
			if(q.getId() == id) return true;
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
		//Need to save to db
		quizzes.add(q);
	}
	
	public void deleteFriendRequest(User requester) {
		Message msg = null;
		for(Message m : friendRequests) {
			if(m.getSender() == requester.getId()) {
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
	
	public void delete() {
		MessageTable.deleteUserMessages(id);
		FriendTable.deleteUserFriends(id);
		QuizTable.deleteUserQuizzes(username);
		UserTable.deleteUser(id);
	}
	
	public void deleteQuiz(int id) {
		Iterator<Quiz> it = quizzes.iterator();
		while(it.hasNext()) {
			Quiz q = it.next();
			if(q.getId() == id) {
				QuizTable.deleteQuiz(id);
				FillBlankTable.deleteQuestion(id);
				MultiChoicePicTable.deleteQuestion(id);
				MultiChoiceTextTable.deleteQuestion(id);
				SingleResponsePicTable.deleteQuestion(id);
				SingleResponseTextTable.deleteQuestion(id);
				it.remove();
				break;
			}
		}
	}
	
	public void awardAchievement(int code) {
		achievements[code] = true;
		AchievementTable.awardAchievement(id, Achievement.getIndex(code));
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
	public ArrayList<Quiz> getQuizzes() {return quizzes;}
	public ArrayList<String> getAchievements() {
		ArrayList<String> text = new ArrayList<String>();
		for(int i = 0; i < achievements.length; i++) {
			if(achievements[i]) text.add(Achievement.getText(i));
		}
		return text;
	}
	
	//Misc
	public boolean equals(User other) {
		if(this.username.equals(other.getUsername())) return true;
		return false;
	}
}
