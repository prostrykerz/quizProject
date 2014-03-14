package messages;

import users.User;

public class Message {
	private int id, sender, receiver;
	private boolean read = false;
	private String message;
	
	public Message(int id, int sender, int receiver, String message) {
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
	
	public int getSender() {
		return sender;
	}
	public int getReceiver() {
		return receiver;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getReadStatus() {
		return read;
	}
}
