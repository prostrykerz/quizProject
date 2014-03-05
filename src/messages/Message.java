package messages;

import users.User;

public class Message {
	private User sender, receiver;
	private boolean read = false;
	private String message;
	private int id;
	private static int ID_GEN = 0;
	
	public Message(User sender, User receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.id = ID_GEN++;
	}
	
	public void setReadStatus(boolean status) {
		this.read = status;
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
