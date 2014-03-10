package messages;

import users.User;

public class Message {
	private int id;
	private User sender, receiver;
	private boolean read = false;
	private String message;
	
	public Message(int id, User sender, User receiver, String message) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
	
	public void setReadStatus(boolean status) {
		this.read = status;
	}
	
	public int getId() {
		return id;
	}
	
	public User getSender() {
		return sender;
	}
	public User getReceiver() {
		return receiver;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getReadStatus() {
		return read;
	}
}
