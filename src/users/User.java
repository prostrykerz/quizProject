package users;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import databases.AchievementTable;
import databases.FillBlankTable;
import databases.FriendTable;
import databases.MessageTable;
import databases.MultiChoicePicTable;
import databases.MultiChoiceTextTable;
import databases.QuizHistoryTable;
import databases.QuizTable;
import databases.SingleResponsePicTable;
import databases.SingleResponseTextTable;
import databases.UserTable;

import messages.Message;
import models.Achievement;
import models.Quiz;
import models.QuizHistory;

public class User {
	private int id;
	private String username;
	private byte[] hash, salt;
	private ArrayList<Message> messages;
	private ArrayList<Integer> friends;
	private ArrayList<Message> friendRequests;
	private ArrayList<Quiz> quizzes;
	private boolean admin;
	
	public User(int id, String username, byte[] salt, byte[] hash, boolean isAdmin, ArrayList<Message> messages, ArrayList<Quiz> quizzes, ArrayList<Integer> friends) {
		this.id = id;
		this.username = username;
		this.admin = isAdmin;
		this.messages = messages;
		this.friendRequests = new ArrayList<Message>();
		this.quizzes = quizzes;
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
			if(fr.getSender() == u.getId()) return true;
		}
		return false;
	}
	
	public void makeAdmin() {
		UserTable.makeAdmin(id);
		admin = true;
	}
	
	public ArrayList<User> getFriendsAsUsers() {
		ArrayList<User> frs = new ArrayList<User>();
		for(int i = 0; i < friends.size(); i++) {
			frs.add(UserTable.getUser(friends.get(i)));
		}
		return frs;
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
		AchievementTable.awardAchievement(id, Achievement.getIndex(code));
	}
	
	public ArrayList<FriendUpdate> getRecentUpdates(int limit) {
		ArrayList<FriendUpdate> updates = new ArrayList<FriendUpdate>();
		ArrayList<User> friends = getFriendsAsUsers();
		for(User fr : friends) {
			ArrayList<Quiz> quizzes = fr.getQuizzes();
			ArrayList<QuizHistory> qhs = QuizHistoryTable.getUserTakenQuizzes(fr);
			ArrayList<Achievement>  achievements = fr.getAchievements();
			for(Quiz q : quizzes) updates.add(new FriendUpdate(q,2));
			for(QuizHistory qh : qhs) updates.add(new FriendUpdate(qh,1));
			for(int i = 0; i < achievements.size(); i++) updates.add(new FriendUpdate(achievements.get(i),3));
		}
		Collections.sort(updates, new Comparator<FriendUpdate>() {
			@Override
			public int compare(FriendUpdate a, FriendUpdate b) {
				return (int)(a.getTime() - b.getTime());
			}
		});
		ArrayList<FriendUpdate> sortedUpdates = new ArrayList<FriendUpdate>();
		for(int i = 0; i < limit; i++) {
			if(i == updates.size()) break;
			sortedUpdates.add(updates.get(i));
		}
		return sortedUpdates;
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
	public ArrayList<Achievement> getAchievements() {return AchievementTable.getAchievements(id);}
	
	//Misc
	public boolean equals(User other) {
		if(this.username.equals(other.getUsername())) return true;
		return false;
	}
}
