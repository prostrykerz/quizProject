package messages;

import users.User;

public abstract class Message {
	User sender, receiver;
	boolean read = false;
	String message;
	
	public User getSender() {
		return sender;
	}
	public User getReceiver() {
		return receiver;
	}
	
	public String getMessage() {
		return message;
	}
}
